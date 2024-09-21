const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const path = require('path');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static('public'));

// API Endpoint to handle timestamps
app.get('/api/timestamp/:date_string?', (req, res) => {
    const { date_string } = req.params;
    let date;

    // Check if date_string is a number (Unix timestamp)
    if (!date_string) {
        date = new Date();
    } else if (!isNaN(date_string)) {
        date = new Date(parseInt(date_string));
    } else {
        date = new Date(date_string);
    }

    // Validate date
    if (date.toString() === 'Invalid Date') {
        return res.json({ error: 'Invalid Date' });
    }

    // Return JSON response
    res.json({
        unix: date.getTime(),
        utc: date.toUTCString(),
    });
});

// Serve the frontend
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

// Start the server
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
