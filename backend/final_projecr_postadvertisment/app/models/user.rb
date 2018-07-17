class User < ApplicationRecord
	has_many :advertisments, :dependent => :destroy

	validates :full_name, presence: true
	validates :address, presence: true
	validates_format_of :email, :with => /\A[^@\s]+@([^@\s]+\.)+[^@\s]+\z/	
	validates :email, presence: true
	validates :password, presence: true, length: { minimum: 6 }
end
