package com.example.progmobile_android.model.dao;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.progmobile_android.model.entity.Card;
import com.example.progmobile_android.model.entity.Event;
import com.example.progmobile_android.model.entity.Pair;
import com.example.progmobile_android.model.entity.Purchase;
import com.example.progmobile_android.model.entity.Ticket;
import com.example.progmobile_android.model.util.Constants;
import com.example.progmobile_android.model.util.ServerCallback;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseDAO {
    private Gson gson;
    private Context context;
    private String url = Constants.URL;

    public PurchaseDAO(Context context) {
        this.context = context;
        this.gson = new Gson();
    }

    public void getListPurchases(int userId, String token, final ServerCallback serverCallback) {
        final String endPoint = url + "/purchases";

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                endPoint,
                response -> {
                    Log.d("getListPurchases", response);
                    try {
                        List<Purchase> listPurchase = new ArrayList<>();
                        JSONArray jsonFromResponse = new JSONArray(response);
                        for (int i = 0; i < jsonFromResponse.length(); i++) {
                            JSONObject object = jsonFromResponse.getJSONObject(i);
                            Purchase purchase = gson.fromJson(object.toString(), Purchase.class);
                            listPurchase.add(purchase);

                            List<Ticket> list = purchase.getTickets();
                            Event event = purchase.getEvent();
                            list.forEach(ticket ->
                                    ticket.setTicketType(event
                                            .getTicket_types()
                                            .stream()
                                            .filter(ticketType ->
                                                    ticketType.getId() == ticket.getTicketTypeId())
                                            .findFirst().get()));
                        }
                        serverCallback.onSuccess(listPurchase);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        serverCallback.onError(null);
                    }
                },
                error -> {
                    Log.d("getListPurchases", error.toString());
                    serverCallback.onError(null);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("userId", String.valueOf(userId));
                headers.put("token", token);
                return headers;
            }
        };

        Repository.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void setPurchase(int userId, String token, Card card, int eventId, List<Pair> list, final ServerCallback serverCallback) {
        final String endPoint = url + "/purchases/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                endPoint,
                response -> {
                    Log.d("setPurchase", response);
                    final Purchase purchase = gson.fromJson(response, Purchase.class);
                    serverCallback.onSuccess(purchase);
                },
                error -> {
                    Log.d("setPurchase", error.toString());
                    serverCallback.onError(null);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("userId", String.valueOf(userId));
                headers.put("token", token);
                return headers;
            }

            @Override
            public byte[] getBody() {
                HashMap<String, String> params = new HashMap<>();
                params.put("card", gson.toJson(card));
                params.put("eventId", String.valueOf(eventId));
                params.put("tickets", gson.toJson(list));
                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        Repository.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void getPurchase(int userId, String token, int purchaseId, final ServerCallback serverCallback) {
        final String endPoint = url + "/purchases/" + purchaseId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                endPoint,
                response -> {
                    Log.d("getPurchase", response);
                    Purchase purchase = gson.fromJson(response, Purchase.class);

                    List<Ticket> list = purchase.getTickets();
                    Event event = purchase.getEvent();
                    list.forEach(ticket ->
                            ticket.setTicketType(event
                                    .getTicket_types()
                                    .stream()
                                    .filter(ticketType ->
                                            ticketType.getId() == ticket.getTicketTypeId())
                                    .findFirst().get()));

                    serverCallback.onSuccess(purchase);
                },
                error -> {
                    Log.d("getPurchase", error.toString());
                    serverCallback.onError(null);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("userId", String.valueOf(userId));
                headers.put("token", token);
                return headers;
            }
        };

        Repository.getInstance(context).addToRequestQueue(stringRequest);
    }
}
