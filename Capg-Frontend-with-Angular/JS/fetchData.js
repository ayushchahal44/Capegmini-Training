let api03 = "https://api.escuelajs.co/api/v1/products";

function fetchDataUsingAxios() {
  axios.get(api03)
    .then(response => {
      // response.data is array
      response.data.forEach(item => {
        console.log(item.title, "-", item.category.name);
      });
    })
    .catch(error => {
      console.log(error);
    });
}

fetchDataUsingAxios();