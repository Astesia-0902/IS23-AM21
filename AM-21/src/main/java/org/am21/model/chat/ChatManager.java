package org.am21.model.chat;

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


    public void handlePublicChatMessage(Player sender, String message){
        publicChatMessages.add(sender.getNickname() + ": " + message);
        if(publicChatMessages.size()>50){
            publicChatMessages.remove(0);
        }
    }
















}
