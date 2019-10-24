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

package com.adobe.devcamp.model;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class Publisher {
    private final String name;
    private final Set<Domain> domains;
    private final List<Integer> advertisers;
    private final List<User> users;

    public Publisher(String name, Set<Domain> domains, List<Integer> advertisers, List<User> users) {
        this.name = name;
        this.domains = domains;
        this.advertisers = advertisers;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public Set<Domain> getDomains() {
        return domains;
    }

    public List<Integer> getAdvertisers() {
        return advertisers;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Publisher)) {
            return false;
        }
        final Publisher publisher = (Publisher) o;

        return Objects.equals(getName(), publisher.getName()) &&
                Objects.equals(getDomains(), publisher.getDomains()) &&
                Objects.equals(getAdvertisers(), publisher.getAdvertisers()) &&
                Objects.equals(getUsers(), publisher.getUsers());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getDomains(), getAdvertisers(), getUsers());
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "name='" + name + '\'' +
                ", domains=" + domains +
                ", advertisers=" + advertisers +
                ", users=" + users +
                '}';
    }
}
