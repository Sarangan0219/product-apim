/*
 * WSO2 API Manager - Publisher API
 * This specifies a **RESTful API** for WSO2 **API Manager** - Publisher.  Please see [full swagger definition](https://raw.githubusercontent.com/wso2/carbon-apimgt/v6.0.4/components/apimgt/org.wso2.carbon.apimgt.rest.api.publisher/src/main/resources/publisher-api.yaml) of the API which is written using [swagger 2.0](http://swagger.io/) specification. 
 *
 * OpenAPI spec version: v1.1
 * Contact: architecture@wso2.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package org.wso2.am.integration.clients.publisher.api.v1.dto;

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
 * GraphQLSchemaTypeDTO
 */

public class GraphQLSchemaTypeDTO {
  @SerializedName("type")
  private String type = null;

  @SerializedName("fieldList")
  private List<String> fieldList = null;

  public GraphQLSchemaTypeDTO type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Type found within the GraphQL Schema 
   * @return type
  **/
  @ApiModelProperty(example = "Country", value = "Type found within the GraphQL Schema ")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public GraphQLSchemaTypeDTO fieldList(List<String> fieldList) {
    this.fieldList = fieldList;
    return this;
  }

  public GraphQLSchemaTypeDTO addFieldListItem(String fieldListItem) {
    if (this.fieldList == null) {
      this.fieldList = new ArrayList<>();
    }
    this.fieldList.add(fieldListItem);
    return this;
  }

   /**
   * Array of fields under current type 
   * @return fieldList
  **/
  @ApiModelProperty(example = "[\"code\",\"name\"]", value = "Array of fields under current type ")
  public List<String> getFieldList() {
    return fieldList;
  }

  public void setFieldList(List<String> fieldList) {
    this.fieldList = fieldList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GraphQLSchemaTypeDTO graphQLSchemaType = (GraphQLSchemaTypeDTO) o;
    return Objects.equals(this.type, graphQLSchemaType.type) &&
        Objects.equals(this.fieldList, graphQLSchemaType.fieldList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, fieldList);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GraphQLSchemaTypeDTO {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    fieldList: ").append(toIndentedString(fieldList)).append("\n");
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

