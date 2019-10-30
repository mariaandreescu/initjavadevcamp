/*
 ************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 * Copyright 2018 Adobe Systems Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by all applicable intellectual property laws,
 * including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 ***********************************************************************
 */

package com.adobe.devcamp.service;

import com.adobe.devcamp.dao.GenericDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public final class GenericService<T> {
    private static final Logger logger = LoggerFactory.getLogger(GenericService.class);

    private final GenericDao<T> genericDao;
    private final ObjectMapper objectMapper;

    public GenericService(GenericDao<T> genericDao, ObjectMapper objectMapper) {
        this.genericDao = genericDao;
        this.objectMapper = objectMapper;
    }

    public Map<Integer, T> getAll(Class<T> clazz) {
        final Map<Integer, String> map = genericDao.selectAll(clazz);
        final Map<Integer, T> all = new HashMap<>();

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            final T t;
            try {
                t = objectMapper.readValue(entry.getValue(), clazz);
                all.put(entry.getKey(), t);
            } catch (JsonProcessingException e) {
                logger.error("Object {} could not be deserialized", entry.getValue());
            }
        }

        return all;
    }

    public T getById(Class<T> clazz, int id) {
        final String json = genericDao.selectById(clazz, id);

        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            logger.error("Object {} could not be deserialized", e);
        }

        return null;
    }
}
