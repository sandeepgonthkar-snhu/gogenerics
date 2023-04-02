import array

import mysql.connector


def getConnection():
    try:
        cnx = mysql.connector.connect(user='gadmin_svc', password='huc8Nuh)5QN)EB',
                                      host='drugs-datastore.ctaow84xv9kk.us-east-2.rds.amazonaws.com',
                                      database='drugstore', port=3306)
        return cnx
    except mysql.connector.Error as err:
        print("Something went wrong: {}".format(err))
        raise Exception(err)


def insert_drugs_batched(drugs: array):
    batch_size = 500
    while drugs:
        batched_drugs, drugs = drugs[:batch_size], drugs[batch_size:]
        insert_drugs(batched_drugs)


def insert_drugs(drugs: array):
    try:
        insert_query = """INSERT INTO drugstore.Drugs (drug_id, brand_name, generic_name, manufacturer_name, description, 
        product_type, substance_name, warnings, drug_interactions, precautions, do_not_use, pregnancy_or_breast_feeding, 
        dosage_and_administration) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"""
        records = []
        for drug in drugs:
            record = (
                drug["drug_id"], drug["brand_name"], drug["generic_name"], drug["manufacturer_name"],
                drug["description"],drug["product_type"], drug["substance_name"], drug["warnings"],
                drug["drug_interactions"], drug["precautions"], drug["do_not_use"], drug["pregnancy_or_breast_feeding"],
                drug["dosage_and_administration"])
            records.append(record)
        cnx = getConnection()
        cursor = cnx.cursor()
        cursor.executemany(operation=insert_query, seq_params=records)
    except mysql.connector.Error as e:
        print("Failed to insert record into MySQL table {}".format(e))
    finally:
        cnx.commit()
        cursor.close()
        cnx.close()
