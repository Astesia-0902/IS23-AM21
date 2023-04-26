package org.am21.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage the chat messages
 * Every match has a chat manager
 */
public class ChatManager {
    public List<String> chatMessages = new ArrayList<>();
    private String message;
    private String sender;
    private Match match;

    public ChatManager(Match match) {
        this.match = match;
    }

    /**
     * send the message to all players
     * @param message the message
     * @param sender the sender
     * @throws RemoteException if the remote call fails
     */
    public void sendMessage(String message, String sender) throws RemoteException {
        this.message = message;
        this.sender = sender;
        chatMessages.add(sender + ": " + message);
        for (Player player : match.playerList) {
            player.getController().clientInput.callBack.sendChatMessage(chatMessages.get(chatMessages.size() - 1));
        }
    }

    /**
     * Get the chat messages history
     * @return the chat messages history
     */
    public List<String> getChatMessages() {
        return chatMessages;
    }

    public void sendChat(String message, String sender) throws RemoteException {
        this.message = message;
        this.sender = sender;
        chatMessages.add(sender + ": " + message);
        String lastTenMex="";
        int tmp = chatMessages.size();

        for(int t=0;t<10;t++){
            if(tmp>0){
                tmp--;
            }
        }
        lastTenMex+="< Recent Chat Messages >\n";
        for(int i=tmp;i<chatMessages.size();i++){
            lastTenMex+=chatMessages.get(i);
            if(i==chatMessages.size()-1){
                lastTenMex+=" >> NEW  ";
            }
            lastTenMex+="\n";

        }
        for(Player p: match.playerList){
            p.getController().clientInput.callBack.sendMessageToClient(lastTenMex);
        }

    }
}
