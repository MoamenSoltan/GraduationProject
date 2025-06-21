import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router'
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import dayjs from 'dayjs';

const StudentMaterialForACourse = () => {
    const {courseId} = useParams()
    const axiosPrivate = useAxiosPrivate();
    const [materials, setMaterials] = useState([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate()

    useEffect(() => {
        const fetchMaterials = async () => {
          try {
            setLoading(true);
            const response = await axiosPrivate.get(`/material/course/${courseId}`);
            setMaterials(response.data);
            console.log("materials fetched :",response.data);
            
          } catch {
            setMaterials([]);
          } finally {
            setLoading(false);
          }
        };
        fetchMaterials();
      }, [courseId, axiosPrivate]);


      if (loading) {
        return <div className="md:w-[80%] w-full m-auto mt-10 text-center">Loading materials...</div>;
      }
  return (
    <div className='md:w-[80%] w-full m-auto mt-10'>
              <h2 className="text-2xl font-semibold mb-4 text-gray-800">Course Materials</h2>
      {materials.length > 0 ? (
        <div className="flex w-full flex-row flex-wrap gap-4">
          {materials.map((material) => (
            <div onClick={()=>navigate(`/studentDashboard/material/${courseId}/${material.materialId}`)}
              key={material.materialId}
              className="w-72 bg-white rounded-lg shadow-lg hover:scale-105 hover:shadow-2xl transition-all duration-300 transform cursor-pointer"
            >
              <div className="p-4 space-y-2">
                <h3 className="text-xl font-semibold text-gray-800">{material.title}</h3>
                <p className="text-sm text-gray-600">{material.description}</p>
                <p className="text-xs text-gray-500">Created: {dayjs(material.createdAt).format('MMMM D, YYYY')}</p>
                <a
                  href={material.filePath}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-blue-500 hover:underline block mt-2"
                >
                  View
                </a>
                {material.deleted && (
                  <span className="text-xs text-red-500 font-semibold">Deleted</span>
                )}
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className="text-gray-400 text-center text-lg">No materials found for this course.</div>
      )}
    </div>
  )
}

export default StudentMaterialForACourse