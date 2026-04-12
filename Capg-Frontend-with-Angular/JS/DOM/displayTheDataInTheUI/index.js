// // API URL
// let api = "https://api.escuelajs.co/api/v1/products";

// async function fetchData() {
//   let response = await fetch(api);
//   let data = await response.json();

//   console.log(data);

//   let table = document.getElementById("table");

//   table.innerHTML = `
//     <tr>
//       <th>ID</th>
//       <th>Title</th>
//       <th>Price</th>
//       <th>Image</th>
//     </tr>
//   `;

//   data.slice(0, 10).forEach((item) => {
//     let row = document.createElement("tr");

//     row.innerHTML = `
//       <td>${item.id}</td>
//       <td>${item.title}</td>
//       <td>${item.price}</td>
//       <td><img src="${item.images[0]}" width="80"/></td>
//     `;

//     table.appendChild(row);
//   });
// }

// fetchData();





// API URL
let api = "https://api.escuelajs.co/api/v1/products";

async function fetchData() {
  let response = await fetch(api);
  let data = await response.json();

  console.log(data);
  for(let i =0;i<data.length -1;i++){
    setTimeout(() => {
      let row = document.createElement("tr");

      row.innerHTML = `
        <td>${data[i].id}</td>
        <td>${data[i].price}</td>
        <td>${data[i].title}</td>
        <td>${data[i].description}</td>
      `;

      table.append(row);

    }, i * 1000);
  }
}

fetchData();