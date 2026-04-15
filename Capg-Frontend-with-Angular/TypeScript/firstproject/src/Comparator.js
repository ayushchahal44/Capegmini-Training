"use strict";
function sort(a, com) {
    for (let i = 0; i < a.length - 1; i++) {
        for (let j = 0; j < a.length - 1 - i; j++) {
            if (com.compare(a[j], a[j + 1]) > 0) {
                let temp = a[j];
                a[j] = a[j + 1];
                a[j + 1] = temp;
            }
        }
    }
    return a;
}
let ar = [
    { id: 2, name: 'Bob' },
    { id: 1, name: 'Alice' },
    { id: 3, name: 'Charlie' }
];
let comp = {
    compare: (ob1, ob2) => ob1.id - ob2.id
};
sort(ar, comp);
console.log(ar);
