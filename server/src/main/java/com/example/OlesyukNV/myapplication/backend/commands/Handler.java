package com.example.OlesyukNV.myapplication.backend.commands;

import javax.servlet.http.HttpServletRequest;

import com.example.server.TaskServer;

/**
 * @author Q-OLN
 */
public abstract class Handler {
    public abstract String execute(HttpServletRequest request, TaskServer taskServer);
}
