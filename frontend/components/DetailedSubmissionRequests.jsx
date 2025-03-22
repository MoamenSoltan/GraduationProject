import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router';
import useAxiosPrivate from '../hooks/useAxiosPrivate';
import toast from 'react-hot-toast';

const DetailedSubmissionRequests = () => {
    const { id } = useParams();
    const axiosPrivate = useAxiosPrivate();
    const [request, setRequest] = useState(null);
    const [loading, setLoading] = useState(true);

    const navigate = useNavigate()

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

    const handleAccept = async ()=>{
        try {

            const response = await axiosPrivate.post(`/admin/approve/${id}`)
            toast.success("Submmision accepted successfully")
            navigate("/adminDashboard/submission-Requests")
        } catch (error) {
            toast.error("couldn't accept submission")
        }
    }

    const handleReject = async ()=>{
        try {

            const response = await axiosPrivate.post(`/admin/reject/${id}`)
            toast.success("Submmision rejected successfully")
            navigate("/adminDashboard/submission-Requests")
        } catch (error) {
            toast.error("couldn't reject submission")
        }
    }

    if (loading) return <div className="text-center text-lg">Loading...</div>;
    if (!request) return <div className="text-center text-lg text-red-500">No data found for this request</div>;

    return (
        <div className="max-w-3xl mx-auto p-6 bg-white shadow-md rounded-lg">
            <h2 className="text-2xl font-bold mb-4">Submission Details</h2>

            <div className="grid grid-cols-2 gap-4">
                <div>
                    <p><strong>ID:</strong> {request.id}</p>
                    <p><strong>City:</strong> {request.city}</p>
                    <p><strong>Address:</strong> {request.address}</p>
                    <p><strong>Country:</strong> {request.country}</p>
                    <p><strong>Graduation Year:</strong> {request.graduationYear}</p>
                    <p><strong>High School Name:</strong> {request.highSchoolName}</p>
                    <p><strong>High School GPA:</strong> {request.highSchoolGpa}</p>
                    <p><strong>Phone Number:</strong> {request.phoneNumber}</p>
                </div>

                <div className="flex flex-col space-y-4">
                    <img className="w-40 h-40 rounded-lg" src={request.personalPhoto} alt="Personal" />
                    <img className="w-40 h-40 rounded-lg" src={request.idPhoto} alt="ID" />
                    <a 
                        href={request.highSchoolCertificate} 
                        target="_blank" 
                        rel="noopener noreferrer" 
                        className="text-blue-500 underline"
                    >
                        View High School Certificate
                    </a>

                    {
                        request.status ==="PENDING"
                        &&  <div>
                                <button onClick={handleAccept}>Accept</button>
                                <button onClick={handleReject}>Reject</button>
                            </div>
                    
                    }
                </div>
            </div>
        </div>
    );
};

export default DetailedSubmissionRequests;
