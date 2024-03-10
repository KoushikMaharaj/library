import React, {useEffect, useState} from 'react';
import './App.css';
import axios from "axios";

interface Books {
    id: number;
    name: string;
    author: string;
}

function App() {

    const [books, setBooks] = useState<Books[]>()

    const getAllBooks = async () => {
        try {
            let response = await axios.get(`http://${process.env.REACT_APP_BE_IP_ADDRESS}:8085/book`);
            setBooks(response.data)
        } catch (error) {
            // Handle errors
            console.error("Error fetching data:", error);
        }
    }
    useEffect(()=>{
        console.log('useEffect triggered');
        getAllBooks();
    },[])
    return (
        <div>
            <h1>Book Library</h1>
            {books &&
                <table>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Book Name</th>
                        <th>Author</th>
                    </tr>
                    </thead>
                    <tbody>
                    {books.map(book => (
                        <tr key={book.id}>
                            <td>{book.id}</td>
                            <td>{book.name}</td>
                            <td>{book.author}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            }
        </div>
    );
}

export default App;
