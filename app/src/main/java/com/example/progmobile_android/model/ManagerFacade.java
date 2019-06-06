package com.example.progmobile_android.model;

import android.content.Context;

import com.example.progmobile_android.model.entities.Card;
import com.example.progmobile_android.model.entities.Pair;
import com.example.progmobile_android.model.manager.EventManager;
import com.example.progmobile_android.model.manager.PurchaseManager;
import com.example.progmobile_android.model.manager.UserManager;
import com.example.progmobile_android.model.repository.ServerCallback;

import java.util.List;

public class ManagerFacade {
    private EventManager eventManager;
    private UserManager userManager;
    private PurchaseManager purchaseManager;
    private static ManagerFacade managerFacade;

    private ManagerFacade(Context context) {
        this.eventManager=new EventManager(context);
        this.userManager=new UserManager(context);
        this.purchaseManager=new PurchaseManager(context);
    }

    public static ManagerFacade getInstance(Context context) {
        if (managerFacade == null) {
            managerFacade = new ManagerFacade(context);
        }
        return managerFacade;
    }

    /***
     * Login de usuário
     * @param serverCallback interface para retorno da chamada HTTP
     * @param login login do usuário
     * @param password senha do usuário
     * @return Retorna Object via CallBack. Necessário cast para UserToken.
     */
    public void login(String login, String password, ServerCallback serverCallback) {
        userManager.login(login, password, serverCallback);
    }

    /***
     * Retorna usuário logado no sistema
     * @param serverCallback interface para retorno da chamada HTTP
     * @return Retorna Object via CallBack. Necessário cast para UserToken.
     */
    public void getUser(ServerCallback serverCallback) {
        userManager.getUser(serverCallback);
    }

    /***
     * Logout de usuário
     * @param serverCallback interface para retorno da chamada HTTP
     * @return Sem retorno
     */
    public void logout(ServerCallback serverCallback) {
        userManager.logout(serverCallback);
    }

    /***
     * Criação de usuário
     * @param serverCallback interface para retorno da chamada HTTP
     * @param login login do usuário
     * @param name nome do usuário
     * @param password senha do usuário
     * @param email email do usuário
     * @return Retorna Object via CallBack. Necessário cast para User.
     */
    public void createUser(String login, String name, String password, String email, ServerCallback serverCallback) {
        userManager.createUser(login, name, password, email, serverCallback);
    }

    /***
     * Lista de eventos
     * @param serverCallback interface para retorno da chamada HTTP
     * @return Retorna Object via CallBack. Necessário cast para List<Event>.
     */
    public void getListEvents(ServerCallback serverCallback) {
        eventManager.getListEvents(serverCallback);
    }

    /***
     * Lista de eventos filtrada por nome
     * @param serverCallback interface para retorno da chamada HTTP
     * @param name nome do evento
     * @return Retorna Object via CallBack. Necessário cast para List<Event>.
     */
    public void searchEventByName(String name, ServerCallback serverCallback) {
        eventManager.searchEventByName(name, serverCallback);
    }

    /***
     * Evento selecionado pelo seu ID
     * @param serverCallback interface para retorno da chamada HTTP
     * @param eventId id do evento
     * @return Retorna Object via CallBack. Necessário cast para Event.
     */
    public void getEvent(int eventId, ServerCallback serverCallback) {
        eventManager.getEvent(eventId, serverCallback);
    }

    /***
     * Lista de compras de um usuário
     * @param serverCallback interface para retorno da chamada HTTP
     * @param userId id do usuário
     * @param token token do usuário
     * @return Retorna Object via CallBack. Necessário cast para List<Purchase>.
     */
    public void getListPurchases(int userId, String token, ServerCallback serverCallback) {
        purchaseManager.getListPurchases(userId, token, serverCallback);
    }

    /***
     * Salvar compra
     * @param serverCallback interface para retorno da chamada HTTP
     * @param userId id do usuário
     * @param token token do usuário
     * @param eventId id do evento
     * @param list lista tipo do ingresso e quantidade selecionados pelo usuário
     * @return Retorna Object via CallBack. Necessário cast para Purchase.
     */
    public void setPurchase(int userId, String token, Card card, int eventId, List<Pair> list, ServerCallback serverCallback) {
        purchaseManager.setPurchase(userId, token, card, eventId, list, serverCallback);
    }

    /***
     * Compra de um usuário pelo ID da compra
     * @param serverCallback interface para retorno da chamada HTTP
     * @param userId id do usuário
     * @param token token do usuário
     * @param purchaseId id da compra
     * @return Retorna Object via CallBack. Necessário cast para Purchase.
     */
    public void getPurchase(int userId, String token, int purchaseId, ServerCallback serverCallback) {
        purchaseManager.getPurchase(userId, token, purchaseId, serverCallback);
    }
}
