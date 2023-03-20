from scripts import pc_processing_scripts as pcps
from scripts import pc_database_scripts as pcds
import argparse

parser = argparse.ArgumentParser(description = 'python processes for post command')
parser.add_argument('-in', '--input', help='input pointcloud file')
parser.add_argument('-odir', '--output_directory', help='output raster file')
parser.add_argument('-db', '--db_params', help='database parameters in following order: host, dbname, user, password, port (separate params by ,)')
parser.add_argument('-gs', '--gs_params', help='geoserver parameters in following order: url, user, password (separate params by ,)')
parser.add_argument('-color', '--colorfile', help='color file')
parser.add_argument('-id', '--identifier', help='pointcloud id')
parser.add_argument('-crs', '--coord_ref_sys', help='coordinate reference system (epsg code)')
args = parser.parse_args()

if __name__ == "__main__":
    input_pc = args.input.replace('\\', '/')
    output_directory = args.output_directory.replace('\\', '/')
    output_raster = f"{output_directory + input_pc.split('/')[-1].split('.')[0]}.tif"
    db_params_list = args.db_params.replace(' ', '').split(',')
    db_params_dict = dict({'host': db_params_list[0], 'dbname': db_params_list[1], 'user': db_params_list[2], 'password': db_params_list[3], 'port': db_params_list[4]})
    gs_params_list = args.gs_params.replace(' ', '').split(',')
    gs_params_dict = dict({'url': gs_params_list[0], 'user': gs_params_list[1], 'password': gs_params_list[2]})

    color_file = args.colorfile.replace('\\', '/')
    id = args.identifier


    crs = pcps.get_crs(input_pc)
    boundary = pcps.get_boundary(input_pc)

    if(len(crs)) == 0:
        crs.append(args.coord_ref_sys)

    pcps.generate_raster(input_pc, output_directory, color_file, crs[0], gs_params_dict)
    pcds.write_pc_into_db(input_pc, db_params_dict, crs[0], id)

    print(crs[0])
    if(len(crs) > 1):
        print(crs[1])
    print(boundary)