'use client';

import React, { useState, useEffect, useRef } from 'react';
import Image, { StaticImageData } from 'next/image';
import api from '../services/api';
import imgForService from '@/../public/cortes/corte_example.png';
import { ChevronLeft, ChevronRight } from 'lucide-react';

interface Service {
  name: string;
  price: string;
}

interface ApiCorte {
  id: number;
  nomeCorte: string;
  precoFormatted: string;
}

const ServicesSection: React.FC = () => {
  const [services, setServices] = useState<Service[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const carouselRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const fetchServices = async () => {
      try {
        const response = await api.get<ApiCorte[]>('/cortes');
        const formattedServices = response.data.map((corte) => ({
          name: corte.nomeCorte,
          price: corte.precoFormatted,
        }));
        setServices(formattedServices);
      } catch (err) {
        console.error("Erro ao buscar serviços:", err);
        setError('Não foi possível carregar os serviços. Tente novamente mais tarde.');
      } finally {
        setLoading(false);
      }
    };

    fetchServices();
  }, []);

  const scroll = (direction: 'left' | 'right') => {
    if (carouselRef.current) {
      const scrollAmount = carouselRef.current.offsetWidth;
      carouselRef.current.scrollBy({
        left: direction === 'left' ? -scrollAmount : scrollAmount,
        behavior: 'smooth',
      });
    }
  };

  if (loading) {
    return (
      <section className="bg-white py-20 md:py-28 text-center">
        <p className="text-gray-600">Carregando serviços...</p>
      </section>
    );
  }

  if (error) {
    return (
      <section className="bg-white py-20 md:py-28 text-center">
        <p className="text-red-500 font-semibold">{error}</p>
      </section>
    );
  }

  return (
    <section className="bg-white py-20 md:py-28">
      <div className="container mx-auto px-6">
        <div className="text-center max-w-3xl mx-auto mb-16">
          <h2 className="text-4xl md:text-5xl font-bold text-gray-900">
            Serviços
          </h2>
          <p className="mt-4 text-lg text-gray-600 leading-relaxed">
            Lorem ipsum dolor sit amet consectetur. Etiam mus vitae lectus proin pellentesque mattis
            iaculis. In viverra accumsan sagittis tempus duis a. Sagittis mollis a eu urna eget id.
          </p>
        </div>
        <div className="relative">
          <div
            ref={carouselRef}
            className="flex overflow-x-auto snap-x snap-mandatory scrollbar-hide"
          >
            {services.map((service, index) => (
              <div key={index} className="flex-none w-full sm:w-1/2 lg:w-1/3 snap-center p-4">
                <div className="bg-white rounded-lg shadow-xl overflow-hidden h-full flex flex-col">
                  <div className="relative w-full h-72">
                    <Image
                      src={imgForService}
                      alt={`Imagem do serviço ${service.name}`}
                      fill
                      sizes="(max-width: 640px) 100vw, (max-width: 1024px) 50vw, 33vw"
                      className="object-cover"
                    />
                  </div>
                  <div className="bg-gray-900 text-white p-5 flex justify-between items-center mt-auto">
                    <h3 className="text-xl font-semibold">{service.name}</h3>
                    <span className="bg-gray-800 py-1 px-4 rounded-full font-bold text-lg">
                      {service.price}
                    </span>
                  </div>
                </div>
              </div>
            ))}
          </div>
          <button
            onClick={() => scroll('left')}
            aria-label="Anterior"
            className="absolute top-1/2 left-0 transform -translate-y-1/2 bg-white bg-opacity-70 hover:bg-opacity-100 rounded-full p-2 shadow-md z-10 transition"
          >
            <ChevronLeft className="h-6 w-6 text-gray-800" />
          </button>
          <button
            onClick={() => scroll('right')}
            aria-label="Próximo"
            className="absolute top-1/2 right-0 transform -translate-y-1/2 bg-white bg-opacity-70 hover:bg-opacity-100 rounded-full p-2 shadow-md z-10 transition"
          >
            <ChevronRight className="h-6 w-6 text-gray-800" />
          </button>
        </div>
      </div>
    </section>
  );
};

export default ServicesSection;