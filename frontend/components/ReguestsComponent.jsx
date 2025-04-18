import React, { useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import useAxiosPrivate from '../hooks/useAxiosPrivate';
import toast from 'react-hot-toast';

const RequestsComponent = () => {
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const [requests, setRequests] = useState([]);
    const [loading, setLoading] = useState(false);
    const [searchParams, setSearchParams] = useSearchParams();
    const selectedStatus = searchParams.get('status') || '';
    const [currentPage, setCurrentPage] = useState(1);
    const cardsPerPage = 9; // You can adjust this number as needed

    useEffect(() => {
        const fetchRequests = async () => {
            try {
                if (loading) return;
                setLoading(true);

                // Pass status filter as a query parameter
                const response = await axiosPrivate.get(`/admin/submissions?status=${selectedStatus}`);
                setRequests(response.data);
                console.log('Requests:', response.data);
            } catch (error) {
                toast.error('An error occurred while fetching submission requests');
            } finally {
                setLoading(false);
            }
        };
        fetchRequests();
    }, [selectedStatus]);

    // Handle filter change
    const handleFilterChange = (e) => {
        const status = e.target.value;
        status === 'all' ? setSearchParams({}) : setSearchParams({ status });
    };

    // Calculate the current page's requests
    const indexOfLastRequest = currentPage * cardsPerPage;
    const indexOfFirstRequest = indexOfLastRequest - cardsPerPage;
    const currentRequests = requests.slice(indexOfFirstRequest, indexOfLastRequest);
    console.log("Current Requests:", currentRequests); // Debugging line

    // Handle page change
    const nextPage = () => {
        if (currentPage < Math.ceil(requests.length / cardsPerPage)) {
            setCurrentPage(prevPage => prevPage + 1);
        }
    };

    const prevPage = () => {
        if (currentPage > 1) {
            setCurrentPage(prevPage => prevPage - 1);
        }
    };

    return (
        <div className="w-full p-6 space-y-6">
            <div className="flex justify-between items-center mb-6">
                <h1 className="text-2xl font-semibold text-gray-800">Submission Requests</h1>
                <div>
                    <label className="mr-2 text-lg font-semibold">Filter by Status:</label>
                    <select 
                        value={selectedStatus} 
                        onChange={handleFilterChange} 
                        className="border border-gray-300 p-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                        <option value="all">All</option>
                        <option value="PENDING">Pending</option>
                        <option value="ACCEPTED">Accepted</option>
                        <option value="REJECTED">Rejected</option>
                    </select>
                </div>
            </div>

            {/* Display Requests in Cards */}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {currentRequests.length > 0 ? currentRequests.map((request) => (
                    <div
                        key={request.id}
                        className="bg-white p-4 border border-gray-200 rounded-lg shadow-sm hover:shadow-md cursor-pointer transition"
                        onClick={() => navigate(`/adminDashboard/submission-Requests/${request.id}`)}
                    >
                        <h3 className="text-xl font-semibold text-gray-800">
                            {request.firstname} {request.lastname}
                        </h3>
                        <p className="text-sm text-gray-500">{request.city}</p>
                        <p className="text-sm text-gray-500">{request.address}</p>
                        <p className="text-sm text-gray-500">Graduation Year: {request.graduationYear}</p>
                        <p className={`mt-2 font-semibold ${request.status === 'ACCEPTED' ? 'text-green-500' : request.status === 'REJECTED' ? 'text-red-500' : 'text-yellow-500'}`}>
                            Status: {request.status}
                        </p>
                    </div>
                )) : (
                    <p>No requests available for the selected status.</p>
                )}
            </div>

            {/* Pagination Controls */}
            { (requests.length / cardsPerPage) >=1 &&
            <div className="flex justify-center items-center space-x-4 mt-6">
                <button 
                    onClick={prevPage} 
                    className="px-4 py-2 bg-gray-300 text-gray-700 rounded-lg hover:bg-gray-400 disabled:opacity-50"
                    disabled={currentPage === 1}
                >
                    Previous
                </button>
                <span className="text-lg font-semibold">
                    Page {currentPage} of {Math.ceil(requests.length / cardsPerPage)}
                </span>
                <button 
                    onClick={nextPage} 
                    className="px-4 py-2 bg-gray-300 text-gray-700 rounded-lg hover:bg-gray-400 disabled:opacity-50"
                    disabled={currentPage === Math.ceil(requests.length / cardsPerPage)}
                >
                    Next
                </button>
            </div>
}

            {/* Loading Indicator */}
            {loading && (
                <div className="text-center py-4">
                    <span className="text-lg text-gray-600">Loading...</span>
                </div>
            )}
        </div>
    );
};

export default RequestsComponent;
