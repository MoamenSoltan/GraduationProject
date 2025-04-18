import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router';
import useAxiosPrivate from '../hooks/useAxiosPrivate';
import toast from 'react-hot-toast';

const DetailedSubmissionRequests = () => {
    const { id } = useParams();
    const axiosPrivate = useAxiosPrivate();
    const [request, setRequest] = useState(null);
    const [loading, setLoading] = useState(true);

    const navigate = useNavigate();

    useEffect(() => {
        const fetchRequestDetails = async () => {
            try {
                const response = await axiosPrivate.get(`/admin/submissions/${id}`);
                setRequest(response.data);
            } catch (error) {
                toast.error('Error fetching submission details');
            } finally {
                setLoading(false);
            }
        };

        fetchRequestDetails();
    }, [id]);

    const handleAccept = async () => {
        if(loading)
            return
        setLoading(true)
        try {
            await axiosPrivate.post(`/admin/approve/${id}`);
            toast.success('Submission accepted successfully');
            navigate('/adminDashboard/submission-Requests');
        } catch (error) {
            toast.error("Couldn't accept submission");
        }finally {setLoading(false)}
    };

    const handleReject = async () => {
        if (loading)
            return
        setLoading(true)
        try {
            await axiosPrivate.post(`/admin/reject/${id}`);
            toast.success('Submission rejected successfully');
            navigate('/adminDashboard/submission-Requests');
        } catch (error) {
            toast.error("Couldn't reject submission");
        }finally{setLoading(false)}
    };

    if (loading) return <div className="text-center text-lg">Loading...</div>;
    if (!request) return <div className="text-center text-lg text-red-500">No data found for this request</div>;

    return (
        <div className="max-w-3xl mx-auto p-6 bg-white shadow-lg rounded-lg border border-gray-200">
            <h2 className="text-3xl font-semibold mb-6 text-center">Submission Details</h2>

            <div className="grid md:grid-cols-2 gap-8">
                <div className="space-y-4">
                    <p><strong>ID:</strong> {request.id}</p>
                    <p><strong>City:</strong> {request.city}</p>
                    <p><strong>Address:</strong> {request.address}</p>
                    <p><strong>Country:</strong> {request.country}</p>
                    <p><strong>Graduation Year:</strong> {request.graduationYear}</p>
                    <p><strong>High School Name:</strong> {request.highSchoolName}</p>
                    <p><strong>High School GPA:</strong> {request.highSchoolGpa}</p>
                    <p><strong>Phone Number:</strong> {request.phoneNumber}</p>
                </div>

                <div className="space-y-4 flex flex-col items-center justify-center">
                    <img className="w-40 h-40 rounded-full border-4 border-gray-200 shadow-md" src={request.personalPhoto} alt="Personal" />
                  
                    <a 
                        href={request.highSchoolCertificate} 
                        target="_blank" 
                        rel="noopener noreferrer" 
                        className="text-blue-500 underline hover:text-blue-600"
                    >
                        View High School Certificate
                    </a>

                    {request.status === "PENDING" && (
                        <div className="flex gap-4 mt-6">
                            <button 
                                onClick={handleAccept} 
                                className="px-6 py-2 bg-green-500 text-white rounded-full hover:bg-green-600 transition-all"
                            >
                                Accept
                            </button>
                            <button 
                                onClick={handleReject} 
                                className="px-6 py-2 bg-red-500 text-white rounded-full hover:bg-red-600 transition-all"
                            >
                                Reject
                            </button>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default DetailedSubmissionRequests;
