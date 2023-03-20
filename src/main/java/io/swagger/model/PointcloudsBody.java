package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PointcloudsBody
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-08T12:14:47.659Z[GMT]")


public class PointcloudsBody   {
  @JsonProperty("file")
  private Resource file = null;

  @JsonProperty("pointcloudId")
  private Integer pointcloudId = null;

  @JsonProperty("metadata")
  private String metadata = null;

  public PointcloudsBody file(Resource file) {
    this.file = file;
    return this;
  }

  /**
   * LAZ file to upload
   * @return file
   **/
  @Schema(required = true, description = "LAZ file to upload")
      @NotNull

    @Valid
    public Resource getFile() {
    return file;
  }

  public void setFile(Resource file) {
    this.file = file;
  }

  public PointcloudsBody pointcloudId(Integer pointcloudId) {
    this.pointcloudId = pointcloudId;
    return this;
  }

  /**
   * ID property of the pointcloud
   * @return pointcloudId
   **/
  @Schema(required = true, description = "ID property of the pointcloud")
      @NotNull

    public Integer getPointcloudId() {
    return pointcloudId;
  }

  public void setPointcloudId(Integer pointcloudId) {
    this.pointcloudId = pointcloudId;
  }

  public PointcloudsBody metadata(String metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Get metadata
   * @return metadata
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getMetadata() {
    return metadata;
  }

  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PointcloudsBody pointcloudsBody = (PointcloudsBody) o;
    return Objects.equals(this.file, pointcloudsBody.file) &&
        Objects.equals(this.pointcloudId, pointcloudsBody.pointcloudId) &&
        Objects.equals(this.metadata, pointcloudsBody.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(file, pointcloudId, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PointcloudsBody {\n");
    
    sb.append("    file: ").append(toIndentedString(file)).append("\n");
    sb.append("    pointcloudId: ").append(toIndentedString(pointcloudId)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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
