export function populateLaptops(element: HTMLTableElement, apiUrl: string) {
  fetch(apiUrl)
    .then(response => response.json())
    .then(laptops => {
      laptops.forEach((laptop: any) => {
        const row = element.insertRow();
        const brandCell = row.insertCell();
        const modelCell = row.insertCell();
        const priceCell = row.insertCell();

        brandCell.innerHTML = laptop.brand;
        modelCell.innerHTML = laptop.model;
        priceCell.innerHTML = `₹${laptop.price.toLocaleString()}`;
      });
    });
}
