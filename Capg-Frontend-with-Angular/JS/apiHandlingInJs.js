// ---------------- FETCH (PROMISE) ----------------
let api01 = "https://api.escuelajs.co/api/v1/products";

function fetchDataFromBackend() {
    fetch(api01)
    .then(res => res.json())
    .then(data => console.log(data))
    .catch(err => console.log(err));
}


// ---------------- FETCH (ASYNC/AWAIT) ----------------
let api02 = "https://api.github.com/users";

async function fetchDataFromBackendAsync() {
    try {
        let response = await fetch(api02);
        let data = await response.json();
        console.log(data);
    } catch (e) {
        console.error(e);
    }
}


// ---------------- AXIOS ----------------
let api03 = "https://api.escuelajs.co/api/v1/products";

function fetchDataUsingAxios() {
    axios.get(api03)
    .then(response => {
        console.log(response.data);
    })
    .catch(error => {
        console.log(error);
    });
}

// fetchDataUsingAxios();


// ---------------- NOTES ----------------
/*
1. fetch → needs .json()
2. axios → direct data (response.data)
3. async/await → cleaner syntax
4. Always handle errors
5. HttpClient → Angular (not Node.js)
*/