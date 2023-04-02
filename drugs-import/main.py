import ijson
from dao import *
from os import listdir


def get_firstElement(dic_object: dict, key_name: str):
    results = dic_object.get(key_name, None)
    return results[0] if results is not None else None


def arry_to_csv(dic_object: dict, key_name: str):
    results = dic_object.get(key_name, None)
    return ",".join(results) if results is not None else None


def build_data(result):
    data = {}
    data["drug_id"] = result["id"]
    data["description"] = get_firstElement(result, "description")
    data["warnings"] = arry_to_csv(result, "boxed_warning") if arry_to_csv(result, "boxed_warning") is not None else arry_to_csv(result, "warnings")
    data["drug_interactions"] = arry_to_csv(result, "drug_interactions")
    data["precautions"] = arry_to_csv(result, "precautions")
    data["do_not_use"] = arry_to_csv(result, "do_not_use")
    data["pregnancy_or_breast_feeding"] = arry_to_csv(result, "pregnancy_or_breast_feeding")
    data["dosage_and_administration"] = arry_to_csv(result, "dosage_and_administration")
    openfda = result["openfda"]
    data["brand_name"] = get_firstElement(openfda, "brand_name")
    data["generic_name"] = arry_to_csv(openfda, "generic_name")
    data["manufacturer_name"] = get_firstElement(openfda, "manufacturer_name")
    data["product_type"] = arry_to_csv(openfda, "product_type")
    data["substance_name"] = arry_to_csv(openfda, "substance_name")



    return data


if __name__ == '__main__':
    path = "C:\\Users\\sgonthkar\\Downloads\\fdadrugs\\extracts\\";
    for file in listdir(path):
        with open(path + file, "rb") as f:
            data_nodes = []
            for result in ijson.items(f, 'results.item'):
                data_node = build_data(result)
                if data_node.get("brand_name") is not None:
                    data_nodes.append(data_node)
            print(str(file)  + ' : ' + str(len(data_nodes)))
            insert_drugs_batched(data_nodes)


