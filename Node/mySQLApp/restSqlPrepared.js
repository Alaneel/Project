// testSqlPrepared.js
const mysql = require('mysql');

// Create database connection
const conn = mysql.createConnection({
    host: 'localhost',
    port: '3306',
    user: 'root',
    password: '!Wym031009',
    database: "coffeeshop" // database to be used
});

// Connect to the MySQL Database server
conn.connect(err => {
    if (err) throw err;
    console.log('connected...');

    // Select all rows
    let sql = `SELECT * FROM beverage WHERE price < ?`; // ? for placeholder
    conn.query(sql, [5.6], (err, rset, fields) => { // Array gives the actual parameters
        if (err) throw err;
        // console.log(fields); // debugging
        console.log(rset); // debugging
        // [
        //  RowDataPacket { id: 1, name: 'Espresso', price: 5.5 },
        // ]

        // Processing the reulst set
        rset.forEach((item, index) => console.log(`${item.name}, ${item.price}`));
        // Espresso, 5.5

        // Close the database connection
        conn.end(err => {
            if (err) throw err;
                console.log('disconnected');
        });
    });
});