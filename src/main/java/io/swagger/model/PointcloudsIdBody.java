package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.PointcloudMetadata;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PointcloudsIdBody
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-08T12:14:47.659Z[GMT]")


public class PointcloudsIdBody   {
  @JsonProperty("file")
  private Resource file = null;

  @JsonProperty("metadata")
  private PointcloudMetadata metadata = null;

  public PointcloudsIdBody file(Resource file) {
    this.file = file;
    return this;
  }

  /**
   * LAZ file to replace
   * @return file
   **/
  @Schema(required = true, description = "LAZ file to replace")
      @NotNull

    @Valid
    public Resource getFile() {
    return file;
  }

  public void setFile(Resource file) {
    this.file = file;
  }

  public PointcloudsIdBody metadata(PointcloudMetadata metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Get metadata
   * @return metadata
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public PointcloudMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(PointcloudMetadata metadata) {
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
    PointcloudsIdBody pointcloudsIdBody = (PointcloudsIdBody) o;
    return Objects.equals(this.file, pointcloudsIdBody.file) &&
        Objects.equals(this.metadata, pointcloudsIdBody.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(file, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PointcloudsIdBody {\n");
    
    sb.append("    file: ").append(toIndentedString(file)).append("\n");
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
