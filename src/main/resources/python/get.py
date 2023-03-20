from scripts import pc_database_scripts as pcds
import argparse

parser = argparse.ArgumentParser(description = 'python processes for get command')
parser.add_argument('-odir', '--output_directory', help='output raster file')
parser.add_argument('-db', '--db_params', help='database parameters in following order: host, dbname, user, password, port (separate params by ,)')
parser.add_argument('-id', '--identifier', help='pointcloud id')
args = parser.parse_args()

if __name__ == "__main__":
    output_directory = args.output_directory.replace('\\', '/')
    db_params_list = args.db_params.replace(' ', '').split(',')
    db_params_dict = dict({'host': db_params_list[0], 'dbname': db_params_list[1], 'user': db_params_list[2], 'password': db_params_list[3], 'port': db_params_list[4]})
    id = args.identifier

    pcds.read_pc_from_db('pointcloud_' + id, db_params_dict, output_directory)