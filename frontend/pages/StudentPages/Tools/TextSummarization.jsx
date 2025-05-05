import React, { useState } from 'react';
import toast from 'react-hot-toast';

const TextSummarization = () => {
  const [file, setFile] = useState(null);
  const [responseData, setResponseData] = useState(null);
  const [loading, setLoading] = useState(false);

  const API_KEY = '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';

  const encodePdfToBase64 = (file) => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onloadend = () => resolve(reader.result.split(',')[1]);
      reader.onerror = reject;
      reader.readAsDataURL(file);
    });
  };

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    setResponseData(null);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!file) return toast.error('Please upload a PDF file');

    try {
      setLoading(true);
      const base64Pdf = await encodePdfToBase64(file);
      const dataUrl = `data:application/pdf;base64,${base64Pdf}`;

      const messages = [
        {
          role: "user",
          content: [
            { type: "text", text: "What are the main points in this document?" },
            { type: "file", file: { filename: file.name, file_data: dataUrl } },
          ],
        },
      ];

      const payload = {
        model: "google/gemma-3-27b-it",
        messages,
        plugins: [{ id: "file-parser", pdf: { engine: "pdf-text" } }],
      };

      const res = await fetch('https://openrouter.ai/api/v1/chat/completions', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${API_KEY}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      });

      const result = await res.json();
      setResponseData(result);
    } catch (err) {
      console.error('Error:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-xl mx-auto p-6 bg-white rounded-xl shadow-sm border border-gray-200">
      <h1 className="text-xl font-semibold text-gray-700 mb-4 text-center">
        Summarize Your PDF
      </h1>

      <form onSubmit={handleSubmit} className="space-y-4">
        <input
          type="file"
          accept="application/pdf"
          onChange={handleFileChange}
          className="block w-full px-4 py-2 text-sm text-gray-700 bg-gray-50 border border-gray-300 rounded-md focus:outline-none focus:ring-1 focus:ring-blue-400"
        />

        <button
          type="submit"
          disabled={loading}
          className="w-full flex items-center justify-center gap-2 px-4 py-2 text-sm font-medium text-white bg-blue-500 hover:bg-blue-600 rounded-md transition disabled:opacity-50"
        >
          {loading ? (
            <div className="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin" />
          ) : (
            "Generate Summary"
          )}
        </button>
      </form>

      {responseData && (
        <div className="mt-6 p-4 rounded-md bg-gray-50 text-sm text-gray-800 border border-gray-200">
          <h2 className="font-medium mb-2 text-gray-700">Summary Output</h2>
          <pre className="whitespace-pre-wrap">
            {responseData?.choices?.[0]?.message?.content || "No content available."}
          </pre>
        </div>
      )}
    </div>
  );
};

export default TextSummarization;
