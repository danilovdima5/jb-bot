package kz.jussan.bot.service;

import kz.jussan.bot.model.ClimateControlRequest;
import kz.jussan.bot.model.Request;

import java.util.List;

public interface ClimateControlService {

    String registerRequest(Request request);
    List<Request> showRequests(String userId);
    String cancelRequest(Long requestId);
    List<ClimateControlRequest> checkActiveRequests();

}