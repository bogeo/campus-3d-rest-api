from scripts import pc_processing_scripts as pcps
import argparse
parser = argparse.ArgumentParser(description = 'python processes for delete command')

parser.add_argument('-gs', '--gs_params', help='geoserver parameters in following order: url, user, password (separate params by ,)')
parser.add_argument('-id', '--identifier', help='pointcloud id')
args = parser.parse_args()

if __name__ == "__main__":

    gs_params_list = args.gs_params.replace(' ', '').split(',')
    gs_params_dict = dict({'url': gs_params_list[0], 'user': gs_params_list[1], 'password': gs_params_list[2]})

    pcps.delete_datastore(gs_params_dict, args.identifier)