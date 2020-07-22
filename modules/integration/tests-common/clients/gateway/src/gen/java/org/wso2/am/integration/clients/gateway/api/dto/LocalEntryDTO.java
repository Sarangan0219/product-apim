/*
 * WSO2 API Manager - Gateway
 * This document specifies a **RESTful API** for WSO2 **API Manager** - Gateway. Please see [full swagger definition](https://raw.githubusercontent.com/wso2/carbon-apimgt/v6.5.176/components/apimgt/org.wso2.carbon.apimgt.rest.api.admin/src/main/resources/admin-api.yaml) of the API which is written using [swagger 2.0](http://swagger.io/) specification. 
 *
 * OpenAPI spec version: v1
 * Contact: architecture@wso2.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package org.wso2.am.integration.clients.gateway.api.dto;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * LocalEntryDTO
 */

public class LocalEntryDTO {
  @SerializedName("deployedLocalEntries")
  private List<String> deployedLocalEntries = null;

  @SerializedName("UnDeployedLocalEntries")
  private List<String> unDeployedLocalEntries = null;

  public LocalEntryDTO deployedLocalEntries(List<String> deployedLocalEntries) {
    this.deployedLocalEntries = deployedLocalEntries;
    return this;
  }

  public LocalEntryDTO addDeployedLocalEntriesItem(String deployedLocalEntriesItem) {
    if (this.deployedLocalEntries == null) {
      this.deployedLocalEntries = new ArrayList<>();
    }
    this.deployedLocalEntries.add(deployedLocalEntriesItem);
    return this;
  }

   /**
   * The local entries which has been deployed in the gateway 
   * @return deployedLocalEntries
  **/
  @ApiModelProperty(value = "The local entries which has been deployed in the gateway ")
  public List<String> getDeployedLocalEntries() {
    return deployedLocalEntries;
  }

  public void setDeployedLocalEntries(List<String> deployedLocalEntries) {
    this.deployedLocalEntries = deployedLocalEntries;
  }

  public LocalEntryDTO unDeployedLocalEntries(List<String> unDeployedLocalEntries) {
    this.unDeployedLocalEntries = unDeployedLocalEntries;
    return this;
  }

  public LocalEntryDTO addUnDeployedLocalEntriesItem(String unDeployedLocalEntriesItem) {
    if (this.unDeployedLocalEntries == null) {
      this.unDeployedLocalEntries = new ArrayList<>();
    }
    this.unDeployedLocalEntries.add(unDeployedLocalEntriesItem);
    return this;
  }

   /**
   * The local entries which has not been deployed in the gateway 
   * @return unDeployedLocalEntries
  **/
  @ApiModelProperty(value = "The local entries which has not been deployed in the gateway ")
  public List<String> getUnDeployedLocalEntries() {
    return unDeployedLocalEntries;
  }

  public void setUnDeployedLocalEntries(List<String> unDeployedLocalEntries) {
    this.unDeployedLocalEntries = unDeployedLocalEntries;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocalEntryDTO localEntry = (LocalEntryDTO) o;
    return Objects.equals(this.deployedLocalEntries, localEntry.deployedLocalEntries) &&
        Objects.equals(this.unDeployedLocalEntries, localEntry.unDeployedLocalEntries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deployedLocalEntries, unDeployedLocalEntries);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocalEntryDTO {\n");
    
    sb.append("    deployedLocalEntries: ").append(toIndentedString(deployedLocalEntries)).append("\n");
    sb.append("    unDeployedLocalEntries: ").append(toIndentedString(unDeployedLocalEntries)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

