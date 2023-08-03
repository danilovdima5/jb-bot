package kz.jussan.bot.repository;

import kz.jussan.bot.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository {

    Request saveRequest(Request request);
    List<Request> findActiveRequestsByUserId(String userId);
    Long updateRequest(Request request);
    List<Request> findActiveRequestByType(Request.Type type);
    Optional<Request> findById(Long requestId);
}
