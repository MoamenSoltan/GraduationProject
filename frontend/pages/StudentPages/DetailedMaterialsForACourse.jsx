import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import toast from 'react-hot-toast';

const DetailedMaterialsForACourse = () => {
  const { courseId, materialId } = useParams();
  const axiosPrivate = useAxiosPrivate();
  const [material, setMaterial] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchMaterial = async () => {
      try {
        const response = await axiosPrivate.get(`/material/${materialId}/course/${courseId}`);
        setMaterial(response.data);
        console.log('Material fetched:', response.data);
      } catch (error) {
        toast.error('Error fetching material');
      } finally {
        setLoading(false);
      }
    };

    fetchMaterial();
  }, [courseId, materialId, axiosPrivate]);

  if (loading) {
    return (
      <div className="w-full text-center py-20 text-gray-500 text-xl">
        Loading material...
      </div>
    );
  }

  if (!material) {
    return (
      <div className="w-full text-center py-20 text-red-500 text-lg">
        Material not found.
      </div>
    );
  }

  return (
    <div className="md:w-[60%] w-full mx-auto mt-10 px-4">
      <div className="bg-white shadow-lg rounded-lg p-6 space-y-4 border border-gray-100">
        <h2 className="text-2xl font-semibold text-gray-800">{material.title}</h2>
        <p className="text-gray-700">{material.description}</p>

        <div className="text-sm text-gray-500">
          Created at:{' '}
          <span className="font-medium">
            {new Date(material.createdAt).toLocaleDateString()}
          </span>
        </div>

        <a
          href={material.filePath}
          target="_blank"
          rel="noopener noreferrer"
          className="inline-block bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition duration-200"
        >
          View / Download File
        </a>

        {material.deleted && (
          <div className="text-red-500 text-sm font-semibold">This material has been deleted.</div>
        )}
      </div>
    </div>
  );
};

export default DetailedMaterialsForACourse;
