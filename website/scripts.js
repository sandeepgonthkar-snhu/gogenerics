const search_drug_url = `http://ec2-18-217-89-60.us-east-2.compute.amazonaws.com:8080/drugs-service/find?brand_name=`;
const get_drug_url = `http://ec2-18-217-89-60.us-east-2.compute.amazonaws.com:8080/drugs-service/`;

function selected_result_load(data) {
    let tableBody = document.querySelector("#selected-results tbody");
	tableBody.innerHTML = "";

    tableBody.appendChild(get_row_for_details('Name', data.brand_name));
	tableBody.appendChild(get_row_for_details('Manufacturer', data.manufacturer_name));
	tableBody.appendChild(get_row_for_details('Description', data.description));
	tableBody.appendChild(get_row_for_details('Product Type', data.product_type));
	tableBody.appendChild(get_row_for_details('Substance(s)', data.substance_name));
	tableBody.appendChild(get_row_for_details('Warnings', data.warnings));
	tableBody.appendChild(get_row_for_details('Drug Interactions', data.drug_interactions));
	tableBody.appendChild(get_row_for_details('Precautions', data.precautions));
	tableBody.appendChild(get_row_for_details('Do Not Use', data.do_not_use));
	tableBody.appendChild(get_row_for_details('Pregancy / Breast Feeding', data.pregnancy_or_breastfeeding));
	tableBody.appendChild(get_row_for_details('Dosage / Administration', data.dosage_and_administration));
}

function get_row_for_details(attribute_name, attribute_value) {
	let row = document.createElement("tr");
	let attribute_name_ele = document.createElement("td");
	let attribute_val_ele = document.createElement("td");

	attribute_name_ele.textContent = attribute_name;
	attribute_val_ele.textContent = attribute_value;
	row.appendChild(attribute_name_ele);
	row.appendChild(attribute_val_ele);
	return row;
}

function seach_for_generics(data) {
    const generics_arr = data.generic_name.split(",");
    var first = 1;
    generics_arr.forEach(generic_name => {
        search_generics(generic_name);
        first = 0;
    })
}

function search_generics(generic_name, clear_records) {
    var temp_url = search_drug_url + generic_name;
	// Make API request
	data = get_api(temp_url);
	load_generics(data, clear_records);
}

function get_api(url) {
	return fetch(url)
		.then(response => response.json())
		.then(data => {
			return data; // Return the JSON data
		})
		.catch(error => {
			console.error('Error fetching data:', error);
		});
}

function load_generics(response_data, clear_table) {
    let tableBody = document.querySelector("#generic-drugs-results tbody");
    if (clear_table = 1) {
        // Clear existing results
	    tableBody.innerHTML = "";
    }

    response_data.then(data => {
		// Loop through API response and add new results to table
		data.forEach(item => {

            if(tableBody.getElementsByTagName("tr").length < 20) {
                let row = document.createElement("tr");
                let generic_name = document.createElement("td");
                let manufacturer_name = document.createElement("td");
                let product_type = document.createElement("td");
                let id = document.createElement("td");
    
                
                generic_name.appendChild(get_anchor_tag_for_modal('#named_drug_modal', 'data-drug-id', item.id, item.brand_name));
                manufacturer_name.textContent = item.manufacturer_name;
                product_type.textContent = item.product_type;
                id.textContent = item.id;
    
                // Add hidden class to ID and Address fields
                id.classList.add("hidden");
                row.appendChild(generic_name);
                row.appendChild(manufacturer_name);
                row.appendChild(product_type);
                row.appendChild(id);
    
                tableBody.appendChild(row);
            }
		});
	})
}

function named_modal_onload(event) {
	var drug_id = event.relatedTarget.getAttribute('data-drug-id');
	var temp_url = get_drug_url + drug_id
	response_data = get_api(temp_url)

	let tableBody = document.querySelector("#named-results tbody");
	tableBody.innerHTML = "";

	response_data.then(data => {
		tableBody.appendChild(get_row_for_details('Name', data.brand_name));
		tableBody.appendChild(get_row_for_details('Generic Name', data.generic_name));
		tableBody.appendChild(get_row_for_details('Manufacturer', data.manufacturer_name));
		tableBody.appendChild(get_row_for_details('Description', data.description));
		tableBody.appendChild(get_row_for_details('Product Type', data.product_type));
		tableBody.appendChild(get_row_for_details('Substance(s)', data.substance_name));
		tableBody.appendChild(get_row_for_details('Warnings', data.warnings));
		tableBody.appendChild(get_row_for_details('Drug Interactions', data.drug_interactions));
		tableBody.appendChild(get_row_for_details('Precautions', data.precautions));
		tableBody.appendChild(get_row_for_details('Do Not Use', data.do_not_use));
		tableBody.appendChild(get_row_for_details('Pregancy / Breast Feeding', data.pregnancy_or_breastfeeding));
		tableBody.appendChild(get_row_for_details('Dosage / Administration', data.dosage_and_administration));
	});
}

function get_row_for_details(attribute_name, attribute_value) {
	let row = document.createElement("tr");
	let attribute_name_ele = document.createElement("td");
	let attribute_val_ele = document.createElement("td");

	attribute_name_ele.textContent = attribute_name;
	attribute_val_ele.textContent = attribute_value;
	row.appendChild(attribute_name_ele);
	row.appendChild(attribute_val_ele);
	return row;
}

function get_anchor_tag_for_modal(modal_name, data_att_name, data_att_value, text_content) {
	let anchor_element = document.createElement("a");
	anchor_element.setAttribute('href', '#');
	anchor_element.setAttribute('data-toggle', 'modal');
	anchor_element.setAttribute('data-target', modal_name);
	anchor_element.setAttribute(data_att_name, String(data_att_value));
	anchor_element.textContent = text_content
	return anchor_element;
}