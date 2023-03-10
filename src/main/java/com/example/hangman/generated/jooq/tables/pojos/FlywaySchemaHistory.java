/*
 * This file is generated by jOOQ.
 */
package com.example.hangman.generated.jooq.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.processing.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class FlywaySchemaHistory implements Serializable {

    private static final long serialVersionUID = 1266128392;

    private Integer   installedRank;
    private String    version;
    private String    description;
    private String    type;
    private String    script;
    private Integer   checksum;
    private String    installedBy;
    private Timestamp installedOn;
    private Integer   executionTime;
    private Boolean   success;

    public FlywaySchemaHistory() {}

    public FlywaySchemaHistory(FlywaySchemaHistory value) {
        this.installedRank = value.installedRank;
        this.version = value.version;
        this.description = value.description;
        this.type = value.type;
        this.script = value.script;
        this.checksum = value.checksum;
        this.installedBy = value.installedBy;
        this.installedOn = value.installedOn;
        this.executionTime = value.executionTime;
        this.success = value.success;
    }

    public FlywaySchemaHistory(
        Integer   installedRank,
        String    version,
        String    description,
        String    type,
        String    script,
        Integer   checksum,
        String    installedBy,
        Timestamp installedOn,
        Integer   executionTime,
        Boolean   success
    ) {
        this.installedRank = installedRank;
        this.version = version;
        this.description = description;
        this.type = type;
        this.script = script;
        this.checksum = checksum;
        this.installedBy = installedBy;
        this.installedOn = installedOn;
        this.executionTime = executionTime;
        this.success = success;
    }

    public Integer getInstalledRank() {
        return this.installedRank;
    }

    public void setInstalledRank(Integer installedRank) {
        this.installedRank = installedRank;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScript() {
        return this.script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Integer getChecksum() {
        return this.checksum;
    }

    public void setChecksum(Integer checksum) {
        this.checksum = checksum;
    }

    public String getInstalledBy() {
        return this.installedBy;
    }

    public void setInstalledBy(String installedBy) {
        this.installedBy = installedBy;
    }

    public Timestamp getInstalledOn() {
        return this.installedOn;
    }

    public void setInstalledOn(Timestamp installedOn) {
        this.installedOn = installedOn;
    }

    public Integer getExecutionTime() {
        return this.executionTime;
    }

    public void setExecutionTime(Integer executionTime) {
        this.executionTime = executionTime;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final FlywaySchemaHistory other = (FlywaySchemaHistory) obj;
        if (installedRank == null) {
            if (other.installedRank != null)
                return false;
        }
        else if (!installedRank.equals(other.installedRank))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        }
        else if (!version.equals(other.version))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        }
        else if (!description.equals(other.description))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        }
        else if (!type.equals(other.type))
            return false;
        if (script == null) {
            if (other.script != null)
                return false;
        }
        else if (!script.equals(other.script))
            return false;
        if (checksum == null) {
            if (other.checksum != null)
                return false;
        }
        else if (!checksum.equals(other.checksum))
            return false;
        if (installedBy == null) {
            if (other.installedBy != null)
                return false;
        }
        else if (!installedBy.equals(other.installedBy))
            return false;
        if (installedOn == null) {
            if (other.installedOn != null)
                return false;
        }
        else if (!installedOn.equals(other.installedOn))
            return false;
        if (executionTime == null) {
            if (other.executionTime != null)
                return false;
        }
        else if (!executionTime.equals(other.executionTime))
            return false;
        if (success == null) {
            if (other.success != null)
                return false;
        }
        else if (!success.equals(other.success))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.installedRank == null) ? 0 : this.installedRank.hashCode());
        result = prime * result + ((this.version == null) ? 0 : this.version.hashCode());
        result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
        result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
        result = prime * result + ((this.script == null) ? 0 : this.script.hashCode());
        result = prime * result + ((this.checksum == null) ? 0 : this.checksum.hashCode());
        result = prime * result + ((this.installedBy == null) ? 0 : this.installedBy.hashCode());
        result = prime * result + ((this.installedOn == null) ? 0 : this.installedOn.hashCode());
        result = prime * result + ((this.executionTime == null) ? 0 : this.executionTime.hashCode());
        result = prime * result + ((this.success == null) ? 0 : this.success.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FlywaySchemaHistory (");

        sb.append(installedRank);
        sb.append(", ").append(version);
        sb.append(", ").append(description);
        sb.append(", ").append(type);
        sb.append(", ").append(script);
        sb.append(", ").append(checksum);
        sb.append(", ").append(installedBy);
        sb.append(", ").append(installedOn);
        sb.append(", ").append(executionTime);
        sb.append(", ").append(success);

        sb.append(")");
        return sb.toString();
    }
}
