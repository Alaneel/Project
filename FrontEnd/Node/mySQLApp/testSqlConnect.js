// testSqlConnect.js
const mysql = require('mysql');

// Create database connection
const conn = mysql.createConnection({
    host: 'localhost',
    port: '3306',
    user: 'root',
    password: '!Wym031009'
});

// Connect to the MySQL Database server
conn.connect(err => {
    if (err) throw err;
    console.log('connected...');

    // Run a SELECT query
    let sql = `SELECT 1 + 1 As ans`;
    conn.query(sql, (err, rset, fields) => {
        if (err) throw err;
        console.log(rset); // debugging
        console.log(fields); // debugging
        console.log('The ans is: ', rset[0].ans);

        // Close the database connection
        conn.end(err => {
            if (err) throw err;
            console.log('disconnected...');
        });
    });
});