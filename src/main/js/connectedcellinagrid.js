process.stdin.resume();
process.stdin.setEncoding('ascii');

var input_stdin = "";
var input_stdin_array = "";
var input_currenttoken = 0;

process.stdin.on('data', function(data) {
	input_stdin += data;
});

process.stdin.on('end', function() {
	input_stdin_array = input_stdin.split(/\s+/);
	main();
});

function readToken() {
	return parseInt(input_stdin_array[input_currenttoken++]);
}

function countRegion(i, j, grid, visited) {
    let n = grid.length;
    let m = grid[0].length;
    
    if (i < 0 || n <= i || j < 0 || m <= j) {
        return 0;
    }
    
    if (visited[i][j]) {
        return 0;
    }
 
    visited[i][j] = true;

    if (grid[i][j] == 0) {
        return 0;
    }
 
    let connected = 1;
    connected += countRegion(i - 1, j - 1, grid, visited);
    connected += countRegion(i - 1, j, grid, visited);
    connected += countRegion(i - 1, j + 1, grid, visited);
    connected += countRegion(i, j - 1, grid, visited);
    connected += countRegion(i, j + 1, grid, visited);
    connected += countRegion(i + 1, j - 1, grid, visited);
    connected += countRegion(i + 1, j, grid, visited);
    connected += countRegion(i + 1, j + 1, grid, visited);

    return connected;
}
   
function main() {
	let n = readToken();
	let m = readToken();
	let grid = [];

	for (grid_i = 0; grid_i < n; grid_i++) {
		var row = [];
		for (grid_j = 0; grid_j < m; grid_j++) {
			row.push(readToken());
		}
		grid.push(row);
	}
	
    let maxRegion = 0;
    let visited = [];
    for (let i=0; i<n; i++) {
    	visited[i] = [];
    }

    for (let i = 0; i < n; i++) {
        for (let j = 0; j < m; j++) {
            if (visited[i][j]) {
                continue;
            }

            let region = countRegion(i, j, grid, visited);

            if (region > maxRegion) {
                maxRegion = region;
            }
        }
    }
    
    console.log(maxRegion);
}