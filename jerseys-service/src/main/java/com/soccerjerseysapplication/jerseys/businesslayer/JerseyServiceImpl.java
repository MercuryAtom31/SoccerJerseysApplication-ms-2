package com.soccerjerseysapplication.jerseys.businesslayer;

import com.soccerjerseysapplication.jerseys.dataaccesslayer.Jersey;
import com.soccerjerseysapplication.jerseys.dataaccesslayer.JerseyIdentifier;
import com.soccerjerseysapplication.jerseys.dataaccesslayer.JerseyRepository;
import com.soccerjerseysapplication.jerseys.mapperlayer.JerseyRequestMapper;
import com.soccerjerseysapplication.jerseys.mapperlayer.JerseyResponseMapper;
import com.soccerjerseysapplication.jerseys.presentationlayer.JerseyRequestModel;
import com.soccerjerseysapplication.jerseys.presentationlayer.JerseyResponseModel;
import com.soccerjerseysapplication.jerseys.utils.exceptions.JerseyNotFoundException;
import com.soccerjerseysapplication.jerseys.utils.exceptions.OutOfStockException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JerseyServiceImpl implements JerseyService {

    private final JerseyRepository jerseyRepository;
    private final JerseyResponseMapper jerseyResponseMapper;
    private final JerseyRequestMapper jerseyRequestMapper;

    public JerseyServiceImpl(JerseyRepository jerseyRepository, JerseyResponseMapper jerseyResponseMapper, JerseyRequestMapper jerseyRequestMapper) {
        this.jerseyRepository = jerseyRepository;
        this.jerseyResponseMapper = jerseyResponseMapper;
        this.jerseyRequestMapper = jerseyRequestMapper;
    }

    @Override
    public List<JerseyResponseModel> getAllJerseys() {
        // Implement your logic to retrieve jerseys based on jerseyId and queryParams
        // For example:
        List<Jersey> jerseys = jerseyRepository.findAll(); // Replace with your query
        return jerseyResponseMapper.entityListToResponseModelList(jerseys);
    }

    @Override
    public JerseyResponseModel getJerseyById(String jerseyId) {
        // Implement your logic to retrieve a jerseys by jerseyId
        Jersey jersey = jerseyRepository.findByJerseyIdentifier_JerseyId(jerseyId);
        if (jersey != null) {
            return jerseyResponseMapper.entityToResponseModel(jersey);
        } else {
            // Handle the case where jerseys is not found
            return null;
        }
    }

    @Override
    public JerseyResponseModel updateJersey(String jerseyId, JerseyRequestModel jerseyRequestModel) {
        // Implement your logic to update a jerseys
        Jersey existingJersey = jerseyRepository.findByJerseyIdentifier_JerseyId(jerseyId);
        if (existingJersey != null) {
            Jersey updatedJersey = jerseyRequestMapper.requestModelToEntity(
                    jerseyRequestModel, existingJersey.getJerseyIdentifier());
            return jerseyResponseMapper.entityToResponseModel(jerseyRepository.save(updatedJersey));
        } else {
            // Handle the case where jerseys is not found
            return null;
        }
    }

    @Override
    public JerseyResponseModel addJersey(JerseyRequestModel jerseyRequestModel) {
        // Implement your logic to add a new jerseys
        Jersey jersey = jerseyRequestMapper.requestModelToEntity(jerseyRequestModel, new JerseyIdentifier());
        return jerseyResponseMapper.entityToResponseModel(jerseyRepository.save(jersey));
    }

    @Override
    public void deleteJersey(String jerseyId) {
        // Implement your logic to delete a jerseys
        Jersey jersey = jerseyRepository.findByJerseyIdentifier_JerseyId(jerseyId);
        if (jersey != null) {
            jerseyRepository.delete(jersey);
        } else {
            // Handle the case where jerseys is not found
        }
    }


    /**
     * @Transactional, means each method will run within a transactional context.
     * This ensures that the operations within each method are completed
     * successfully before committing the transaction to the database.
     * If an exception occurs, the transaction will roll back.
     */
    @Transactional
    public void addJerseyQuantity(Long jerseyId, int quantityToAdd) {
        Jersey jersey = jerseyRepository.findById(jerseyId)
                .orElseThrow(() -> new JerseyNotFoundException("Jersey not found with ID: " + jerseyId));

        if (quantityToAdd <= 0) {
            throw new IllegalArgumentException("Quantity to add must be positive.");
        }

        jersey.setQuantity(jersey.getQuantity() + quantityToAdd);
        jerseyRepository.save(jersey);
    }

    @Transactional
    public void reduceJerseyQuantity(Long jerseyId, int quantityToReduce) {
        Jersey jersey = jerseyRepository.findById(jerseyId)
                .orElseThrow(() -> new JerseyNotFoundException("Jersey not found with ID: " + jerseyId));

        if (quantityToReduce <= 0) {
            throw new IllegalArgumentException("Quantity to reduce must be positive.");
        }

        if (jersey.getQuantity() < quantityToReduce) {
            throw new OutOfStockException("Insufficient stock for Jersey ID: " + jerseyId);
        }

        jersey.setQuantity(jersey.getQuantity() - quantityToReduce);
        jerseyRepository.save(jersey);
    }
}
