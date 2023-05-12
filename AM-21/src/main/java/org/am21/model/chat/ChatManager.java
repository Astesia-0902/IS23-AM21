package org.am21.model.chat;

import org.am21.controller.CommunicationController;
import org.am21.model.Match;
import org.am21.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manage the chat messages
 * Every match has a chat manager
 */
public class ChatManager{
    public List<String> publicChatMessages = new ArrayList<>();
    private Match match;

    public ChatManager(Match match) {
        this.match = match;
    }



    /**
     * Get the chat messages history
     *
     * @return the chat messages history
     */
    public List<String> getPublicChatMessages() {
        return publicChatMessages;
    }

    public void sendChat(String message, String sender) {
        //this.message = message;
        //this.sender = sender;
        publicChatMessages.add(sender + ": " + message);
        String lastTenMex = "";
        int tmp = publicChatMessages.size();

        for (int t = 0; t < 10; t++) {
            if (tmp > 0) {
                tmp--;
            }
        }
        lastTenMex += "\n< Recent Chat Messages >\n";
        for (int i = tmp; i < publicChatMessages.size(); i++) {
            lastTenMex += publicChatMessages.get(i);
            if (i == publicChatMessages.size() - 1) {
                lastTenMex += " >> NEW\n\nPress 'Enter'";
            }
            lastTenMex += "\n";

        }
        for (Player p : match.playerList) {
            CommunicationController.instance.sendMessageToClient(lastTenMex, true, p.getController());
        }

    }

    public void handlePublicChatMessage(Player sender, String message){
        publicChatMessages.add(sender.getNickname() + ": " + message);
        if(publicChatMessages.size()>50){
            publicChatMessages.remove(0);
        }
    }
















}
