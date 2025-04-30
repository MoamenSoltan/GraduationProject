import React, { useState } from 'react';
import axios from 'axios';

const ImageToText = () => {
  const [image, setImage] = useState(null);
  const [base64Image, setBase64Image] = useState('');
  const [responseText, setResponseText] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [inputText, setInputText] = useState(''); // State to store user input text

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setBase64Image(reader.result.split(',')[1]); // Get base64 without the "data:image/jpeg;base64,"
      };
      reader.readAsDataURL(file);
      setImage(file);
    }
  };

  const handleSubmit = async () => {
    if (!base64Image || !inputText) return; // Ensure both image and input text are provided

    setIsLoading(true);
    try {
      const response = await axios.post(
        'https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-exp:generateContent?key=AIzaSyAy3GBEN0_INMha94VR57EOWGMAsO-I8kU',
        {
          contents: [
            {
              parts: [
                { text: inputText }, // Use the user input text
                {
                  inline_data: {
                    mime_type: 'image/jpeg',
                    data: base64Image,
                  },
                },
              ],
            },
          ],
        },
        {
          headers: {
            'Content-Type': 'application/json',
          },
        }
      );

      setResponseText(response.data);
    } catch (error) {
      console.error('Error during API request:', error);
    } finally {
      setIsLoading(false);
    }
  };

  // Remove the first and last quotes
  const cleanedText = responseText
    ? responseText.candidates[0]?.content?.parts[0]?.text.slice(0, -1) // Remove first and last quote
    : '';

  return (
    <div className="max-w-xl mx-auto p-6 bg-white rounded-xl shadow-md border border-gray-200">
      <h2 className="text-2xl font-semibold text-center text-gray-700 mb-4">
        Image to Text Converter
      </h2>

      <input
        type="file"
        accept="image/*"
        onChange={handleImageChange}
        className="block w-full px-4 py-2 mb-4 text-sm text-gray-700 bg-gray-50 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
      />
      {image && <p className="text-center text-gray-600">Image selected: {image.name}</p>}

      {/* Text input for user to add custom text */}
      <input
        type="text"
        value={inputText}
        onChange={(e) => setInputText(e.target.value)}
        placeholder="Enter your text here"
        className="block w-full px-4 py-2 mb-4 text-sm text-gray-700 bg-gray-50 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
      />

      <button
        onClick={handleSubmit}
        disabled={isLoading}
        className="w-full py-2 px-4 text-white bg-blue-500 rounded-md hover:bg-blue-600 disabled:bg-gray-400 transition-colors"
      >
        {isLoading ? (
          <div className="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin mx-auto" />
        ) : (
          'Convert Image to Text'
        )}
      </button>

      {responseText && (
        <div className="mt-6 p-6 bg-gray-100 rounded-md border border-gray-300">
          <h3 className="text-xl font-semibold text-gray-700 mb-2">Response Text:</h3>
          <p className="text-lg text-gray-800 whitespace-pre-wrap">{cleanedText}</p>
        </div>
      )}
    </div>
  );
};

export default ImageToText;
