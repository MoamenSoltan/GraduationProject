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
    
    // Get the selected status from the URL (default: "all")
    const selectedStatus = searchParams.get('status') || 'all';

    useEffect(() => {
        const fetchRequests = async () => {
            try {
                if (loading) return;
                setLoading(true);

                // Pass status filter as a query parameter
                const response = await axiosPrivate.get(`/admin/submissions?status=${selectedStatus}`);
                setRequests(response.data);
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
        setSearchParams({ status: e.target.value });
    };

    return (
        <div className='w-full p-4'>
            <div className="mb-4">
                <label className="mr-2 text-lg font-semibold">Filter by Status:</label>
                <select 
                    value={selectedStatus} 
                    onChange={handleFilterChange} 
                    className="border border-gray-300 p-2 rounded"
                >
                    <option value="all">All</option>
                    <option value="pending">Pending</option>
                    <option value="accepted">Accepted</option>
                    <option value="rejected">Rejected</option>
                </select>
            </div>

            <div className='overflow-x-auto'>
                <table className='w-full border-collapse border border-gray-200'>
                    <thead>
                        <tr className='bg-gray-100'>
                            <th className='border p-2'>ID</th>
                            <th className='border p-2'>City</th>
                            <th className='border p-2'>Address</th>
                            <th className='border p-2'>Country</th>
                            <th className='border p-2'>Graduation Year</th>
                            <th className='border p-2'>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        {requests.map((request) => (
                            <tr
                                key={request.id}
                                className='cursor-pointer hover:bg-gray-100'
                                onClick={() => navigate(`/adminDashboard/submission-Requests/${request.id}`)}
                            >
                                <td className='border p-2'>{request.id}</td>
                                <td className='border p-2'>{request.city}</td>
                                <td className='border p-2'>{request.address}</td>
                                <td className='border p-2'>{request.country}</td>
                                <td className='border p-2'>{request.graduationYear}</td>
                                <td className='border p-2 font-bold'>{request.status}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default RequestsComponent;
