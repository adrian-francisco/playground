let rows = 14;
let cols = 7;

for (let i=0; i<rows; i++) {
    let row = "";
    for (let j=0; j<cols; j++) {
        row += "\u2571\u2572";
    }
    document.write(row + "<br/>");
    console.log(row);
}