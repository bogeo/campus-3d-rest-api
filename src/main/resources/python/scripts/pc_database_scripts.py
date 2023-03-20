import subprocess
import json
from osgeo import osr
import pdal
import os
from osgeo import gdal
import sys

def write_pc_into_db(input_pc, db_params, srid, id):
    
    # build and execute pdal pipeline
    json = f"""
    [
        {{
          "type":"readers.las",
          "filename":"{input_pc}"
        }},
        {{
          "type":"filters.chipper",
          "capacity":400
        }},
        {{
          "type":"writers.pgpointcloud",
          "connection":"host='{db_params['host']}' dbname='{db_params['dbname']}' user='{db_params['user']}' password='{db_params['password']}' port='{db_params['port']}'",
          "schema":"pointcloud",
          "table":"pointcloud_{id}",
          "compression":"dimensional",
          "srid":"{srid}"
        }}
    ]
    """
    pipeline = pdal.Pipeline(json)
    count = pipeline.execute()
    
def read_pc_from_db(table_name, db_params, output_dir):
    
    # build and execute pdal pipeline
    json = f"""
    [
    {{
      "type":"readers.pgpointcloud",
      "connection":"host='{db_params['host']}' dbname='{db_params['dbname']}' user='{db_params['user']}' password='{db_params['password']}' port='{db_params['port']}'",
      "schema":"pointcloud",
      "table":"{table_name}",
      "column":"pa"
    }},
    {{
      "type":"writers.las",
      "filename":"{output_dir}pc.laz"
    }}
    ]
    """
    pipeline = pdal.Pipeline(json)
    count = pipeline.execute()

    print(f"{output_dir}pc.laz")
