package kz.jussan.bot.service;

import kz.jussan.bot.model.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClimateControlServiceImpl implements ClimateControlService{

    private static final Map<String, List<Request>> REQUESTS = new HashMap<>();

    @Override
    public String registerRequest(Request request) {
        return null;
    }

    @Override
    public List<Request> showRequests(String userId) {
        return null;
    }

    @Override
    public String cancelRequest(Long requestId) {
        return null;
    }
}
