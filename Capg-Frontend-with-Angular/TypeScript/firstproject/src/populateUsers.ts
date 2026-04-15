export function populateUsers(element: HTMLTableElement,apiUrl: string){
    fetch(apiUrl)
    .then(response=>response.json())
    .then(users=>{
        users.forEach((user: any)=>{
            const row = element.insertRow();
            const nameCell = row.insertCell();
            const emailCell = row.insertCell();

            nameCell.innerHTML = user.name;
            emailCell.innerHTML = user.email;

        })
    })
}