// testSqlSelect.js
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

    // Create Table
    let sql = `CREATE TABLE IF NOT EXISTS beverage (
        id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        name VARCHAR(30) NOT NULL DEFAULT '',
        price DECIMAL(5,2) NOT NULL DEFAULT 999.99,
        PRIMARY KEY (id))`;
    conn.query(sql, (err, result) => {
        if (err) throw err;
        console.log(result); // debugging
        // OkPacket {
        //  fieldCount: 0,   
        //  affectedRows: 0,
        //  insertId: 0,
        //  serverStatus: 2,
        //  warningCount: 1,
        //  message: '',
        //  protocal41: true,
        //  changedRows: 0
        // }
        console.log('created table...');

        // Insert Rows
        let sql = `INSERT INTO beverage VALUES
            (NULL, 'Espresso', 5.5),
            (NULL, 'Cappuccino', 5.6),
            (NULL, 'Latte', 5.7)`;
        conn.query(sql, (err, result) => {
            if (err) throw err;
            console.log(result); // debugging
            // OkPacket {
            //  fieldCount: 0,   
            //  affectedRows: 3,
            //  insertId: 4,
            //  serverStatus: 2,
            //  warningCount: 0,
            //  message: '&Records: 3  Duplicates: 0  Warnings: 0',
            //  protocal41: true,
            //  changedRows: 0
            // } 
            console.log(result.message);
            console.log('insert id is: ' + result.insertId);

            // Select all rows
            let sql = `SELECT * FROM beverage`;
            conn.query(sql, (err, rset, fields) => {
                if (err) throw err;
                console.log(fields); // debugging
                // [
                //  FieldPacket {
                //      catalog: 'def',
                //      db: 'coffeeshoop',
                //      table: 'beverage',
                //      orgTable: 'beverage',
                //      name: 'id',
                //      orgName: 'id',
                //      ......
                //  },
                //  FieldPacket {
                //      name: 'price',
                //      ......
                // },
                //  FieldPacket {
                //      name: 'price',
                //      ......
                // }
                // ]
                console.log(rset); // debugging
                // [
                //  RowDataPacket { id: 1, name: 'Espresso', price: 5.5 },
                //  RowDataPacket { id: 2, name: 'Cappuccino', price: 5.6 },
                //  RowDataPacket { id: 3, name: 'Latte', price: 5.7 },
                // ]

                // Processing the result set
                rset.forEach((item, index) => console.log(`${item.name}, ${item.price}`));
                // Espresso, 5.5
                // Cappuccino, 5.6
                // Latte, 5.7

                // Close the database connection
                conn.end(err => {
                    if (err) throw err;
                        console.log('disconnected...');
                });
            });
        });
    });
});