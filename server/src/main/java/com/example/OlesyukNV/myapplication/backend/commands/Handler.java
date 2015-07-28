package com.example.OlesyukNV.myapplication.backend.commands;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;

import com.example.server.TaskServer;

/**
 * @author Q-OLN
 */
public abstract class Handler {
    public abstract JSONArray execute(HttpServletRequest request, TaskServer taskServer);
}
