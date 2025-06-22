import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import Modal from '../../components/Modal';
import toast from 'react-hot-toast';

const InstructorMaterialForACourse = () => {
  const { courseId } = useParams();
  const axiosPrivate = useAxiosPrivate();

  const [materials, setMaterials] = useState([]);
  const [loading, setLoading] = useState(false);
  const [modal, setModal] = useState(false);
  const [form, setForm] = useState({
    title: '',
    description: '',
    file: null,
  });
  const [uploading, setUploading] = useState(false);

  const [deleteModalOpen, setDeleteModalOpen] = useState(false);
  const [selectedMaterial, setSelectedMaterial] = useState(null);

  useEffect(() => {
    const fetchMaterials = async () => {
      try {
        setLoading(true);
        const response = await axiosPrivate.get(`/material/course/${courseId}`);
        setMaterials(response.data);
        console.log('materials fetched:', response.data);
      } catch {
        setMaterials([]);
      } finally {
        setLoading(false);
      }
    };
    fetchMaterials();
  }, [courseId, axiosPrivate, modal]);

  const handleInputChange = (e) => {
    const { name, value, files } = e.target;
    if (name === 'file') {
      setForm((prev) => ({ ...prev, file: files[0] }));
    } else {
      setForm((prev) => ({ ...prev, [name]: value }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.title || !form.description || !form.file) {
      toast.error('All fields are required.');
      return;
    }
    setUploading(true);
    try {
      const data = new FormData();
      data.append('title', form.title);
      data.append('description', form.description);
      data.append('filePath', form.file);
      data.append('courseId', courseId);

      await axiosPrivate.post(`/material/course/${courseId}`, data, {
        headers : {
          "Content-Type ":"multipart/formData"
        }
      });

      toast.success('Material added successfully!');
      setModal(false);
      setForm({ title: '', description: '', file: null });
    } catch {
      toast.error('Failed to add material.');
    } finally {
      setUploading(false);
    }
  };

  const handleDelete = async () => {
    if (!selectedMaterial) return;
    try {
      await axiosPrivate.delete(`/material/${selectedMaterial.materialId}/course/${courseId}`);
      toast.success('Material deleted successfully');
      setMaterials((prev) =>
        prev.filter((mat) => mat.materialId !== selectedMaterial.materialId)
      );
    } catch (err) {
      toast.error('Failed to delete material');
    } finally {
      setDeleteModalOpen(false);
      setSelectedMaterial(null);
    }
  };

  if (loading) {
    return (
      <div className="md:w-[80%] w-full m-auto mt-10 text-center">
        Loading materials...
      </div>
    );
  }

  return (
    <div className="md:w-[80%] w-full m-auto mt-10">
      <h2 className="text-2xl font-semibold mb-4 text-gray-800">Course Materials</h2>

      {materials.length > 0 ? (
        <div className="flex w-full flex-row flex-wrap gap-4">
          {materials.map((material) => (
            <div
              key={material.materialId}
              className="w-72 bg-white rounded-lg shadow-lg hover:scale-105 hover:shadow-2xl transition-all duration-300 transform"
            >
              <div className="p-4 space-y-2">
                <h3 className="text-xl font-semibold text-gray-800">{material.title}</h3>
                <p className="text-sm text-gray-600">{material.description}</p>
                <p className="text-xs text-gray-500">
                  Created: {new Date(material.createdAt).toLocaleString()}
                </p>
                <a
                  href={material.filePath}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-blue-500 hover:underline block mt-2"
                >
                  View
                </a>
                <div className="flex justify-between items-center pt-2">
                  {material.deleted ? (
                    <span className="text-xs text-red-500 font-semibold">Deleted</span>
                  ) : (
                    <button
                      className="text-red-600 text-sm hover:underline"
                      onClick={() => {
                        setSelectedMaterial(material);
                        setDeleteModalOpen(true);
                      }}
                    >
                      Delete
                    </button>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className="text-gray-400 text-center text-lg">
          No materials found for this course.
        </div>
      )}

      {/* + Button */}
      <button
        onClick={() => setModal(true)}
        className="block mx-auto mt-8 p-4 w-20 h-20 rounded-full bg-blue-600 text-white text-3xl shadow-lg hover:scale-110 transition-all"
        title="Add Material"
      >
        +
      </button>

      {/* Add Material Modal */}
      <Modal open={modal} onClose={() => setModal(false)}>
        <form className="p-6 max-w-md mx-auto space-y-4" onSubmit={handleSubmit}>
          <h2 className="text-xl font-bold mb-2">Add New Material</h2>
          <div>
            <label className="block text-sm font-medium text-gray-700">Title</label>
            <input
              type="text"
              name="title"
              className="w-full border rounded px-3 py-2"
              value={form.title}
              onChange={handleInputChange}
              required
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">Description</label>
            <textarea
              name="description"
              className="w-full border rounded px-3 py-2"
              value={form.description}
              onChange={handleInputChange}
              required
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">File</label>
            <input
              type="file"
              name="file"
              className="w-full border rounded px-3 py-2"
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="flex justify-end gap-2 mt-6">
            <button
              type="button"
              className="bg-gray-200 hover:bg-gray-300 text-gray-800 px-4 py-2 rounded"
              onClick={() => setModal(false)}
              disabled={uploading}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded"
              disabled={uploading}
            >
              {uploading ? 'Uploading...' : 'Add Material'}
            </button>
          </div>
        </form>
      </Modal>

      {/* Delete Confirmation Modal */}
      <Modal open={deleteModalOpen} onClose={() => setDeleteModalOpen(false)}>
        <div className="p-6 max-w-md mx-auto space-y-4">
          <h2 className="text-xl font-bold text-gray-800">Confirm Deletion</h2>
          <p className="text-gray-600">
            Are you sure you want to delete{' '}
            <strong>{selectedMaterial?.title}</strong>?
          </p>
          <div className="flex  gap-2 mt-6 w-full justify-center">
            <button
              className="bg-gray-200 hover:bg-gray-300 text-gray-800 px-4 py-2 rounded"
              onClick={() => setDeleteModalOpen(false)}
            >
              Cancel
            </button>
            <button
              className="bg-red-600 hover:bg-red-700 text-white px-6 py-2 rounded"
              onClick={handleDelete}
            >
              Delete
            </button>
          </div>
        </div>
      </Modal>
    </div>
  );
};

export default InstructorMaterialForACourse;
