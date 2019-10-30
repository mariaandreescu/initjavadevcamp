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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public final class Campaign {
    private final String name;
    private final long startTime;
    private final long endTime;
    private final Integer advertiserId;
    private final Target target;

    @JsonCreator
    public Campaign(@JsonProperty("name") String name,
                    @JsonProperty("startTime") long startTime,
                    @JsonProperty("endTime") long endTime,
                    @JsonProperty("advertiserId") Integer advertiserId,
                    @JsonProperty("target") Target target) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.advertiserId = advertiserId;
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public Integer getAdvertiserId() {
        return advertiserId;
    }

    public Target getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Campaign)) {
            return false;
        }
        final Campaign campaign = (Campaign) o;

        return getStartTime() == campaign.getStartTime() &&
                getEndTime() == campaign.getEndTime() &&
                Objects.equals(getName(), campaign.getName()) &&
                Objects.equals(getAdvertiserId(), campaign.getAdvertiserId()) &&
                Objects.equals(getTarget(), campaign.getTarget());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getStartTime(), getEndTime(), getAdvertiserId(), getTarget());
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", advertiserId=" + advertiserId +
                ", target=" + target +
                '}';
    }

    public final static class Target{
        private final Gender gender;
        private final short minAge;
        private final short maxAge;
        private final List<Domain> interests;

        @JsonCreator
        public Target(@JsonProperty("gender") Gender gender,
                      @JsonProperty("minAge") short minAge,
                      @JsonProperty("maxAge") short maxAge,
                      @JsonProperty("interests") List<Domain> interests) {
            this.gender = gender;
            this.minAge = minAge;
            this.maxAge = maxAge;
            this.interests = interests;
        }

        public Gender getGender() {
            return gender;
        }

        public short getMinAge() {
            return minAge;
        }

        public short getMaxAge() {
            return maxAge;
        }

        public List<Domain> getInterests() {
            return interests;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Target)) {
                return false;
            }
            final Target target = (Target) o;

            return getMinAge() == target.getMinAge() &&
                    getMaxAge() == target.getMaxAge() &&
                    getGender() == target.getGender() &&
                    Objects.equals(getInterests(), target.getInterests());
        }

        @Override
        public int hashCode() {

            return Objects.hash(getGender(), getMinAge(), getMaxAge(), getInterests());
        }

        @Override
        public String toString() {
            return "Target{" +
                    "gender=" + gender +
                    ", minAge=" + minAge +
                    ", maxAge=" + maxAge +
                    ", interests=" + interests +
                    '}';
        }
    }

}
