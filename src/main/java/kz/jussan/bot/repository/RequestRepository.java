package kz.jussan.bot.repository;

import kz.jussan.bot.model.Request;

import java.util.List;

public interface RequestRepository {

    Long saveRequest(Request request);
    List<Request> findActiveRequestsByUserId(String userId);
    Long updateRequest(Request request);
    List<Request> findActiveRequestByType(Request.Type type);
}
