package client;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import model.Chat;
import model.FriendRequest;
import model.User;
import observer.Observer;
import server.ServerInterface;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {

    private final ServerInterface serverObject; // Objeto remoto del servidor
    private User user; // Usuario que se ha logueado en el cliente
    private final ArrayList<Observer> observers; // Observadores del cliente

    // Constructor
    protected ClientImpl(ServerInterface serverObject) throws Exception {

        super();

        this.serverObject = serverObject;

        this.observers = new ArrayList<>();

        new Login(this);

    }

    // Añade un observador al cliente
    public void addObserver(Observer observer) {

        this.observers.add(observer);

    }

    // Elimina un observador del cliente
    public boolean requestFriend(String friendName, String name, String password) {

        try {

            return this.serverObject.requestFriend(this, friendName, name, password);

        } catch (Exception e) {

            System.out.println("Client | Error: " + e.getMessage());
            return false;

        }

    }

    // Inicia el proceso de login
    public boolean requestLogin(String name, String password) {

        try {

            this.user = this.serverObject.login(this, name, password);

            return this.user != null;

        } catch (Exception e) {

            System.out.println("Client | Error: " + e.getMessage());
            return false;

        }

    }

    // Inicia el proceso de validacion para verificar que es el usuario que dice ser
    public boolean requestValidation(String name, String password) {

        try {

            return this.serverObject.validateRequest(this, name, password);

        } catch (Exception e) {

            System.out.println("Client | Error: " + e.getMessage());
            return false;

        }

    }

    // Inicia el proceso de registro
    public boolean requestRegister(String name, String password) {

        try {

            return this.serverObject.register(name, password);

        } catch (Exception e) {

            System.out.println("Client | Error: " + e.getMessage());
            return false;

        }

    }

    // Inicia el proceso de logout
    public void requestLogout() {

        try {

            this.serverObject.logout(this, this.user);

        } catch (Exception e) {

            System.out.println("Client | Error: " + e.getMessage());

        }

    }

    // Intenta aceptar una peticion de amistad
    public boolean requestAcceptFriendRequest(int requestId, String friendName, String name, String password) {

        try {

            boolean res = this.serverObject.acceptFriendRequest(this, requestId, friendName, name, password);

            if (!res) {
                return false;
            }

            this.user.removeFriendRequest(friendName);

            for (Observer observer : this.observers) {

                observer.updateFriendRequests();

            }

            return true;

        } catch (Exception e) {

            System.out.println("Client | Error: " + e.getMessage());
            return false;
        }

    }

    // Intenta rechazar una peticion de amistad
    public boolean requestDeclineFriendRequest(int requestId, String friendName, String name, String password) {

        try {

            boolean res = this.serverObject.declineFriendRequest(this, requestId, friendName, name, password);

            if (!res) {
                return false;
            }

            this.user.removeFriendRequest(friendName);

            for (Observer observer : this.observers) {

                observer.updateFriendRequests();

            }

            return true;

        } catch (Exception e) {

            System.out.println("Client | Error: " + e.getMessage());
            return false;

        }

    }

    // Intenta eliminar a un amigo
    public boolean requestRemoveFriend(User friend, String name, String password) {

        try {

            boolean res = this.serverObject.removeFriend(this, friend.getName(), name, password);

            if (!res) {
                return false;
            }

            this.user.removeConnectedFriend(friend, true);

            for (Observer observer : this.observers) {

                observer.updateConnectedFriends();

            }

            return true;

        } catch (Exception e) {

            System.out.println("Client | Error: " + e.getMessage());
            return false;

        }

    }

    // Intenta enviar un mensaje a un amigo
    @Override
    public void notifyMessage(ClientInterface friendClient, User user, String message) throws RemoteException {

        User friend = null;

        // Buscamos el amigo que nos ha enviado el mensaje
        for (Map.Entry<User, ClientInterface> entry : this.user.getConnectedFriends().entrySet()) {

            if (entry.getValue().equals(friendClient)) {

                friend = entry.getKey();
                break;

            }

        }
        
        // Si no encontramos el amigo, no hacemos nada
        if (friend == null || !friend.equals(user)) {
            return;
        }

        // Añadimos el mensaje al chat
        Chat chat = this.user.getChats().get(friend);

        // Si el chat no existe, lo creamos
        chat.addMessage(message, false);

        // Notificamos a los observadores que se ha actualizado el chat
        for (Observer observer : this.observers) {

            observer.updateChats(friend);

        }

    }

    // Notifica a los observadores que se ha recibido una peticion de amistad
    @Override
    public void notifyFriendRequest(FriendRequest request) throws RemoteException {

        this.user.addFriendRequest(request);

        for (Observer observer : this.observers) {

            observer.updateFriendRequests();

        }

    }

    // Notifica a los observadores de que se ha conectado un amigo
    @Override
    public void notifyConnectedFriend(ClientInterface client, User user) throws RemoteException {

        this.user.addConnectedFriend(user, client);

        for (Observer observer : this.observers) {

            observer.updateConnectedFriends();

        }

    }

    // Notifica a los observadores el usuario se ha desconectado
    @Override
    public void notifyDisconnectedFriend(User user, boolean removeChat) throws RemoteException {

        this.user.removeConnectedFriend(user, removeChat);

        for (Observer observer : this.observers) {

            observer.updateConnectedFriends();

        }

    }

    // Se envía el mensaje y se avisa a los observadores que se ha actualizado el chat
    public void sendMessage(User user, String message) {

        try {

            ClientInterface friendClient = this.user.getConnectedFriends().get(user);

            if (friendClient == null) {
                return;
            }

            friendClient.notifyMessage(this, new User(this.user.getId(), this.user.getName()), message);
            Chat chat = this.user.getChats().get(user);

            chat.addMessage(message, true);

        } catch (Exception e) {

            System.out.println("Client | Error: " + e.getMessage());

        }

    }

    public User getUser() {

        return this.user;

    }

    // Se desconecta del servidor
    public void end() {

        try {

            UnicastRemoteObject.unexportObject(this, true);

        } catch (NoSuchObjectException e) {

            System.out.println("Client | Error: " + e.getMessage());

        }

    }

}
