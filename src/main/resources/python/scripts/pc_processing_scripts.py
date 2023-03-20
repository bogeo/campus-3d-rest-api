import subprocess
import json
from osgeo import osr
import pdal
import os
from osgeo import gdal
from geo.Geoserver import Geoserver

def get_crs(input_pc):
    
    # execute pdal info --metadata
    metadata_result = subprocess.run(['pdal', 'info', input_pc, '--metadata'],
                        stderr = subprocess.PIPE,  # stderr and stdout get
                        stdout = subprocess.PIPE)  # captured as bytestrings

    # decode stdout from bytestring and convert to a dictionary
    metadata = json.loads(metadata_result.stdout.decode())


    # get epsg codes (horizontal and vertical)
    horz_srs = osr.SpatialReference(metadata["metadata"]["srs"]["horizontal"])
    horz_epsg = horz_srs.GetAttrValue("AUTHORITY", 1)
    vert_srs = osr.SpatialReference(metadata["metadata"]["srs"]["vertical"])
    vert_epsg = vert_srs.GetAttrValue("AUTHORITY", 1)

    result = []

    if(horz_epsg != None):
        result.append(horz_epsg)
    if(vert_epsg != None):
        result.append(vert_epsg)
        
    return result
    
    
def get_boundary(input_pc):
    
    # execure pdal info --boundary
    boundary_result = subprocess.run(['pdal', 'info', input_pc, '--boundary'],
                        stderr = subprocess.PIPE,  # stderr and stdout get
                        stdout = subprocess.PIPE)  # captured as bytestrings
    boundary = json.loads(boundary_result.stdout.decode())
    
    # get boundary
    boundary_wkt = boundary['boundary']['boundary']
    #boundary_json = boundary['boundary']['boundary_json']

    return boundary_wkt

def generate_raster(input_pc, output_directory, path_colorfile, epsg, gs_params):

    filename = input_pc.split('/')[-1].split('.')[0]
    
    # build and execute pdal pipeline
    path_dem_bin = output_directory + "dtm.tif"
    json = f"""
    [
            "{input_pc}",
            {{
                "filename":"{path_dem_bin}",
                "gdaldriver":"GTiff",
                "output_type":"all",
                "resolution":"0.75",
                "type": "writers.gdal",
                "override_srs": "EPSG:{epsg}"
            }}
        ]
    """
    pipeline = pdal.Pipeline(json)
    count = pipeline.execute()
    arrays = pipeline.arrays
    metadata = pipeline.metadata
    log = pipeline.log

    geoserver = Geoserver(gs_params['url'], gs_params['user'], gs_params['password'])
    geoserver.create_coveragestore(layer_name =filename, path=path_dem_bin, workspace='campus-3d-dems')

    os.remove(path_dem_bin)

def delete_datastore(gs_params, id):
    geoserver = Geoserver(gs_params['url'], gs_params['user'], gs_params['password'])
    response = geoserver.delete_coveragestore(coveragestore_name='pointcloud_' + id, workspace='campus-3d-dems')
    print(response)