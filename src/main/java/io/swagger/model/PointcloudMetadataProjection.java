package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * EPSG-Code for position and height projection
 */
@Schema(description = "EPSG-Code for position and height projection")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-08T12:14:47.659Z[GMT]")


public class PointcloudMetadataProjection   {
  @JsonProperty("position")
  private String position = null;

  @JsonProperty("height")
  private String height = null;

  public PointcloudMetadataProjection position(String position) {
    this.position = position;
    return this;
  }

  /**
   * Get position
   * @return position
   **/
  @Schema(description = "")
  
    public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public PointcloudMetadataProjection height(String height) {
    this.height = height;
    return this;
  }

  /**
   * Get height
   * @return height
   **/
  @Schema(description = "")
  
    public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PointcloudMetadataProjection pointcloudMetadataProjection = (PointcloudMetadataProjection) o;
    return Objects.equals(this.position, pointcloudMetadataProjection.position) &&
        Objects.equals(this.height, pointcloudMetadataProjection.height);
  }

  @Override
  public int hashCode() {
    return Objects.hash(position, height);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PointcloudMetadataProjection {\n");
    
    sb.append("    position: ").append(toIndentedString(position)).append("\n");
    sb.append("    height: ").append(toIndentedString(height)).append("\n");
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
